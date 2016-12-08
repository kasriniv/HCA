package com.sample;



import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

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
	  KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
   config.setOption( EventProcessingOption.STREAM );
   KieBase kBase = kContainer.newKieBase(config);

   KieSessionConfiguration ksConfig = KieServices.Factory.get().newKieSessionConfiguration();
   KieSession kSession = kBase.newKieSession(ksConfig, null);
   //NO CHANNEL NEEDED FOR THIS TEST kSession.registerChannel(NotificationChannel.CHANNEL_ID, new NotificationChannel());
   
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
       p.setavalue(800);
       p.setcode("GLUCOSE");
       p.setpatientid("abc123");
       
       ep2.insert(ge);
       ep.insert(p);
  
   
       kSession.fireAllRules();
       logger.close();
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
     
      //read all patients and assert
      
      //read all readings and assert
      String readingsFile = "/Volumes/D/rh/bpmworkspace/HCARules/Readings.dat";
      String PatientsFile = "/Volumes/D/rh/bpmworkspace/HCARules/Patients.dat";
      BufferedReader br = null;
      String line = "";
      String cvsSplitBy = ";";

      try {

    	  br = new BufferedReader(new FileReader(PatientsFile));
          while ((line = br.readLine()) != null) {

              // use comma as separator
              String[] patientinfo = line.split(cvsSplitBy);

              System.out.println("Patient: id="+patientinfo[0]+"  code= " + patientinfo[1] + " idealreading=" + patientinfo[2] );
              Patient p= new Patient();
              p.setcode(patientinfo[1]);
              p.setavalue(Integer.parseInt(patientinfo[2]));
              p.setpatientid(patientinfo[0]);
              ep.insert(p);
          }
           br.close();   
          br = new BufferedReader(new FileReader(readingsFile));
          while ((line = br.readLine()) != null) {

              // use comma as separator
              String[] patientinfo = line.split(cvsSplitBy);

              System.out.println("Patient: id="+patientinfo[0]+"  code= " + patientinfo[1] + " reading=" + patientinfo[2] );
              GlucoseEvent ge2= new GlucoseEvent();
              ge2.setcode(patientinfo[1]);
              ge2.setrvalue(Integer.parseInt(patientinfo[2]));
              ge2.setpatientid(patientinfo[0]);
              ep2.insert(ge2);

          }
          br.close();
      } 
      catch (Exception ex){}
      
      //END NEW DATA READ
      /**** data from file isntead
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
          
          
          ****////data from file instead
     
         
          kSession.fireAllRules();
          logger.close();
          System.out.println("alldone");
      }

    

} //end class

