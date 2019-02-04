package com.mvptestapp.core.model;

public class BaseResponseModel {



    private String name;

    private String success_msg;

    private String response_status;

    private String error_msg;



    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSuccess_msg ()
    {
        return success_msg;
    }

    public void setSuccess_msg (String success_msg)
    {
        this.success_msg = success_msg;
    }

    public String getResponse_status ()
    {
        return response_status;
    }

    public void setResponse_status (String response_status)
    {
        this.response_status = response_status;
    }

    public String getError_msg ()
    {
        return error_msg;
    }

    public void setError_msg (String error_msg)
    {
        this.error_msg = error_msg;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", success_msg = "+success_msg+", response_status = "+response_status+", error_msg = "+error_msg+"]";
    }
}
