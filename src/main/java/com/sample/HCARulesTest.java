package com.sample;



import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.rule.EntryPoint;

import com.sample.GlucoseEvent;
import com.sample.Patient;

/**
 * This is a sample class to launch a rule.
 */
public class HCARulesTest {

    public static final void main(String[] args) {
        try {
        	//callSingleAlertRule(); //for the first rule
        	callGlucoseAlertRule(); //for the second rule
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    

     public static void callSingleAlertRule(){
    	 
    	// load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
 	    KieContainer kContainer = ks.getKieClasspathContainer();
     	KieSession kSession = kContainer.newKieSession("ksession-rules");

         // go !
     	
     	
         //for this test
         GlucoseEvent ge= new GlucoseEvent();
         ge.setcode("GLUCOSE");
         ge.setrvalue(700);
         ge.setpatientid("abc123");
        // ge.setreadingdate();
         
         Patient pa= new Patient();
         pa.setcode("GLUCOSE");
         pa.setavalue(200); //this is acceptable level for that code
         pa.setpatientid("abc123");
         /*
         EntryPoint entrypoint = kSession.getEntryPoint("GlucoseStream");
         entrypoint.insert(ge);
         EntryPoint entrypoint2 = kSession.getEntryPoint("PatientStream");
         entrypoint2.insert(pa);
         */
         
        kSession.insert(ge);
         kSession.insert(pa);
         kSession.fireAllRules();
         System.out.println("alldone");
     }
     public static void callGlucoseAlertRule(){
    	 
     	// load up the knowledge base
 	        KieServices ks = KieServices.Factory.get();
  	    KieContainer kContainer = ks.getKieClasspathContainer();
  	  KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
      config.setOption( EventProcessingOption.STREAM );
      KieBase kBase = kContainer.newKieBase(config);

      KieSessionConfiguration ksConfig = KieServices.Factory.get().newKieSessionConfiguration();
      KieSession kSession = kBase.newKieSession(ksConfig, null);
      kSession.registerChannel(NotificationChannel.CHANNEL_ID, new NotificationChannel());
      	//KieSession kSession = kContainer.newKieSession("ksession-rules");
      kSession.addEventListener( new DebugRuleRuntimeEventListener() );
      KieRuntimeLogger logger = ks.getLoggers().newThreadedFileLogger( kSession, "/Volumes/D/temp/HCACEPStreaming", 1000 );


          //for this test
      EntryPoint ep= kSession.getEntryPoint("PatientStream");
      EntryPoint ep2= kSession.getEntryPoint("GlucoseStream");
      
          GlucoseEvent ge= new GlucoseEvent();
          ge.setcode("GLUCOSE");
          ge.setrvalue(800);
          ge.setpatientid("abc123");
          
          
          Patient p =new Patient();
          p.setavalue(451);
          p.setcode("GLUCOSE");
          p.setpatientid("abc123");
          
          ep2.insert(ge);
          ep.insert(p);
          
          GlucoseEvent ge2= new GlucoseEvent();
          ge2.setcode("GLUCOSE");
          ge2.setrvalue(1000);
          ge2.setpatientid("abc1232");
          
          Patient p2 =new Patient();
          p2.setavalue(451);
          p2.setcode("GLUCOSE");
          p2.setpatientid("abc1232");
          ep2.insert(ge2);
          ep.insert(p2);
          
          
          GlucoseEvent ge3= new GlucoseEvent();
          ge3.setcode("GLUCOSE");
          ge3.setrvalue(900);
          ge3.setpatientid("abc1232");
          ep2.insert(ge3);
        
          
          GlucoseEvent ge4= new GlucoseEvent();
          ge4.setcode("GLUCOSE");
          ge4.setrvalue(950);
          ge4.setpatientid("abc123");
          ep2.insert(ge4);
     
          
         ///CHANGING TO STREAM ENTRYPOINT
          /*
          kSession.insert(ge);
          kSession.insert(p);
          kSession.insert(ge2);
          kSession.insert(p2);
          ///////////ONLY IF >2 READINGS >500 IN 1 MINUTE..WE ARE SIMULATING THE THIRD ARRIVING LATE
         
          try {
			Thread.sleep(6);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         
         kSession.insert(ge3);
       kSession.insert(p3);
       */
      
          kSession.fireAllRules();
          logger.close();
          System.out.println("alldone");
      }


} //end class

