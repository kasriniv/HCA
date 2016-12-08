package com.sample;
@org.kie.api.definition.type.Role(org.kie.api.definition.type.Role.Type.EVENT)

public class GlucoseEvent implements java.io.Serializable {

   static final long serialVersionUID = 1L;

   @org.kie.api.definition.type.Label("ID of the Patient")
   private java.lang.String patientid;
   @org.kie.api.definition.type.Label("Code like Glucose,BP etc")
   private java.lang.String code;
   @org.kie.api.definition.type.Label("Actual reading")
   private int rvalue; //might need to change this to int

   @org.kie.api.definition.type.Label(value = "Date of Reading")
   private java.util.Date readingdate;

   public GlucoseEvent(String string, String string2, String string3)
   {
   }
   public GlucoseEvent()
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

   public int getrvalue()
   {
      return this.rvalue;
   }

   public void setrvalue(int rvalue)
   {
      this.rvalue = rvalue;
   }

   public java.util.Date getreadingdate()
   {
      return this.readingdate;
   }

   public void setreadingdate(java.util.Date readingdate)
   {
      this.readingdate = readingdate;
   }

   public GlucoseEvent(java.lang.String patientid, java.lang.String code,
         int rvalue,java.util.Date readingdate)
   {
      this.patientid = patientid;
      this.code = code;
      this.rvalue = rvalue;
      this.readingdate = readingdate;
   }
   public GlucoseEvent(java.lang.String patientid, java.lang.String code,
	         int rvalue)
	   {
	      this.patientid = patientid;
	      this.code = code;
	      this.rvalue = rvalue;
	      //this.readingdate = readingdate;
	   }
}