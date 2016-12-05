package com.sample;
@org.kie.api.definition.type.Role(org.kie.api.definition.type.Role.Type.EVENT)

public class Patient implements java.io.Serializable {

   static final long serialVersionUID = 1L;

   @org.kie.api.definition.type.Label("ID of the Patient")
   private java.lang.String patientid;
   @org.kie.api.definition.type.Label("Code like Glucose,BP etc")
   private java.lang.String code;
   @org.kie.api.definition.type.Label("Acceptable value ")
   private int avalue; //might need to change this to int

  

   public Patient()
   {
   }

   public java.lang.String getpatientid()
   {
      return this.patientid;
   }

   public void setpatientid(java.lang.String patientid)
   {
      this.patientid = patientid;
   }

   public java.lang.String getcode()
   {
      return this.code;
   }

   public void setcode(java.lang.String code)
   {
      this.code = code;
   }

   public int getavalue()
   {
      return this.avalue;
   }

   public void setavalue(int avalue)
   {
      this.avalue = avalue;
   }

   

   public Patient(java.lang.String patientid, java.lang.String code,
         int avalue, java.util.Date readingdate)
   {
      this.patientid = patientid;
      this.code = code;
      this.avalue = avalue;
      
   }



}