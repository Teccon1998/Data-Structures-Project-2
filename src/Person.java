import java.lang.String;
public class Person implements Comparable<Person>
{
  //private data members
    private String FirstName;
    private String LastName;
    private String Address;
    private String State;
    private int zip;
    private String phoneNum;
    

          
    
    //default constructor
     public Person()
     {}
    //Custom constructor
     public Person(String first, String last, String addr, String stat, int z, String number)
        {
          FirstName = first;
          LastName = last;
          Address = addr;
          State = stat;
          zip = z;
          phoneNum = number;
        }
        //mutators
     public void setFirstName(String first)
       {
          FirstName = first;
       }
     public void setLastName(String last)
       {
          LastName = last;
       }
     public void setAddress(String addr)
       {
          Address = addr;
       }
     public void setState(String stat)
       {
          State = stat;
       }
     public void setZip(int z)
       {
          zip = z;
       }
     public void setPhoneNum(String number)
       {
           phoneNum = number;
       }
       //retreival functions
     public String getFirstName()
       {
            return FirstName;
       }
     public String getLastName()
       {
            return LastName;
       }
     public String getAddress()
       {
            return Address;
       }
     public String getState()
       {
           return State;
       }
     public int getZip()
       {
            return zip;
       }
     public String getPhoneNum()
       {
            return phoneNum;
       }
      //compare function for comparing strings inside objects
      public int compareTo(Person per) 
      {
      int num = this.phoneNum.compareTo(per.phoneNum);
      return num;
      }
}
