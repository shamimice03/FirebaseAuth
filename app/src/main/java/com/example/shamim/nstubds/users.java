package com.example.shamim.nstubds;

/**
 * Created by Shamim on 13-Feb-18.
 */

public class users {

   public users() {
   }

   public String first_name;
   public String blood_group;
   public String image;
   public String last_name;
   public String department_name;
   public String birthday;
   public String phone_number;

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public String fullName;



   public String getFirst_name() {
      return first_name;
   }

   public void setFirst_name(String first_name) {
      this.first_name = first_name;
   }

   public String getBlood_group() {
      return blood_group;
   }

   public void setBlood_group(String blood_group) {
      this.blood_group = blood_group;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getLast_name() {
      return last_name;
   }

   public void setLast_name(String last_name) {
      this.last_name = last_name;
   }

   public String getDepartment_name() {
      return department_name;
   }

   public void setDepartment_name(String department_name) {
      this.department_name = department_name;
   }

   public String getBirthday() {
      return birthday;
   }

   public void setBirthday(String birthday) {
      this.birthday = birthday;
   }

   public String getPhone_number() {
      return phone_number;
   }

   public void setPhone_number(String phone_number) {
      this.phone_number = phone_number;
   }

   public users(String first_name, String blood_group, String image, String last_name, String department_name, String birthday, String phone_number, String fullName) {
      this.first_name = first_name;
      this.blood_group = blood_group;
      this.image = image;
      this.last_name = last_name;
      this.department_name = department_name;
      this.birthday = birthday;
      this.phone_number = phone_number;
      this.fullName=fullName;

   }








}
