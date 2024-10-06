package clean.infrastructure;

import java.util.logging.Level;
import java.util.logging.Logger;

import clean.domain.MyServiceInterface;
import clean.domain.NotificationInterface;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.*;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import io.vertx.ext.web.handler.sockjs.SockJSSocket;

public class MyWebController extends AbstractVerticle implements NotificationInterface {

	private int port;
	private MyServiceInterface domainLayer;
    static Logger logger = Logger.getLogger("[MyWebController]");	
    private static final String COUNT_EVENT_TOPIC = "count-events";
    
	public MyWebController(MyServiceInterface domainLayer) {
		this.port = 8080;
		this.domainLayer = domainLayer;
		logger.setLevel(Level.INFO);
	}

	public void launch() {
    	Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(this);		
	}
	
	public void start() {
		logger.log(Level.INFO, "Web server initializing...");
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);

		/* static files by default searched in "webroot" directory */
		router.route("/static/*").handler(StaticHandler.create().setCachingEnabled(false));
		router.route().handler(BodyHandler.create());
		
		router.route(HttpMethod.POST, "/api/count/update").handler(this::processServiceRequestCmd);
		router.route(HttpMethod.GET, "/api/count").handler(this::processServiceRequestQuery);
		
		
		server.webSocketHandler(webSocket -> {
			  if (webSocket.path().equals("/api/count/monitoring")) {
				webSocket.accept();
				logger.log(Level.INFO, "New count monitoring observer registered.");
		    	EventBus eb = vertx.eventBus();
		    	eb.consumer(COUNT_EVENT_TOPIC, msg -> {
		    		JsonObject ev = (JsonObject) msg.body();
			    	logger.log(Level.INFO, "Changes in count: " + ev.encodePrettily());
		    		webSocket.writeTextMessage(ev.encodePrettily());
		    	});
			  } else {
				  logger.log(Level.INFO, "Count monitoring observer rejected.");
				  webSocket.reject();
			  }
			});		
		
		server
		.requestHandler(router)
		.listen(port);

		logger.log(Level.INFO, "Web server ready - port: " + port);
	}
	
	protected void processServiceRequestCmd(RoutingContext context) {
		logger.log(Level.INFO, "New request - use case cmd " + context.currentRoute().getPath());
		JsonObject reply = new JsonObject();
		domainLayer.doMyUseCaseCmd();
		reply.put("result", "ok");
		sendReply(context, reply);
	}
	
	protected void processServiceRequestQuery(RoutingContext context) {
		logger.log(Level.INFO, "New request - query " + context.currentRoute().getPath());
		JsonObject reply = new JsonObject();
		int res = domainLayer.doMyUseCaseQuery();
		reply.put("result", "ok");
		reply.put("value", res);
		sendReply(context, reply);
	}

	public void notifyCountChanged(int newCount) {
		logger.log(Level.INFO, "notify count changed");
		EventBus eb = vertx.eventBus();
		
		JsonObject obj = new JsonObject();
		obj.put("event", "count-changed");
		obj.put("value", newCount);		
    	eb.publish(COUNT_EVENT_TOPIC, obj);
	}
	
	private void sendReply(RoutingContext request, JsonObject reply) {
		HttpServerResponse response = request.response();
		response.putHeader("content-type", "application/json");
		response.end(reply.toString());
	}
	
}
