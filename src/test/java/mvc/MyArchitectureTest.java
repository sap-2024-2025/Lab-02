package mvc;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import org.junit.jupiter.api.Test;

public class MyArchitectureTest {
    @Test
    public void basicMVCRule() {
    	JavaClasses importedClasses = new ClassFileImporter().importPackages("mvc");

    	ArchRule ruleDep1 = 
    			noClasses().that().resideInAPackage("..model..")
    			.should().dependOnClassesThat().resideInAPackage("..view..")
    			.orShould().dependOnClassesThat().resideInAPackage("..controller..");
        
        ruleDep1.check(importedClasses);    
    }
}
