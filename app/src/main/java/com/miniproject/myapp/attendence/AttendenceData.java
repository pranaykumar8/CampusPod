package com.miniproject.myapp.attendence;

public class AttendenceData {

    String StudentRollId;



    public AttendenceData(String StudentRollId) {
        this.StudentRollId = StudentRollId;

    }

    public void setStudentRollId(String StudentRollId) {
        this.StudentRollId = StudentRollId;
    }

    public String getStudentRollId() {
        return StudentRollId.toUpperCase();
    }


    public AttendenceData(){}
    }




