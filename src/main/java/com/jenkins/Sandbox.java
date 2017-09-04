package com.jenkins;

import org.junit.Test;
import com.jenkins.methods.*;

public class Sandbox {
    private String host = "jenkins.orasi.com";
    private String port = "80";
    private String jobPath = "job/OpenSandbox/job/";
    private String jobName = "Toyota-SauceLabs";
    
    private JenkinsClient jenkins = new JenkinsClient(host, port, jobPath);
    
    @Test
    public void getLastBuild(){
	System.out.println("Get Last Build Test");
	
	LastBuild lastBuild  = jenkins.lastBuild(jobName);
	
	System.out.println("Build Number: " + lastBuild.getId());	
	System.out.println("Is Building: " + lastBuild.getBuilding());
	System.out.println("Job Name: " + lastBuild.getFullDisplayName());
	System.out.println("Job Desciption: " + lastBuild.getDescription());
	System.out.println("Job Result: " + lastBuild.getResult());
	System.out.println("Job URL: " + lastBuild.getUrl());
	System.out.println();
	System.out.println();
    }
    
    @Test
    public void getLastCompletedBuild(){
	System.out.println("Get Last Completed Build Test");
	
	LastCompletedBuild lastCompletedBuild  = jenkins.lastCompletedBuild(jobName);
	System.out.println("Build Number: " + lastCompletedBuild.getId());	
	System.out.println("Is Building: " + lastCompletedBuild.getBuilding());
	System.out.println("Job Name: " + lastCompletedBuild.getFullDisplayName());
	System.out.println("Job Desciption: " + lastCompletedBuild.getDescription());
	System.out.println("Job Result: " + lastCompletedBuild.getResult());
	System.out.println("Job URL: " + lastCompletedBuild.getUrl());
	System.out.println();
	System.out.println();
}

    @Test
    public void getLastFailedBuild(){
	System.out.println("Get Last Failed Build Test");
	
	LastFailedBuild lastFailedBuild  = jenkins.lastFailedBuild(jobName);

	System.out.println("Build Number: " + lastFailedBuild.getId());	
	System.out.println("Is Building: " + lastFailedBuild.getBuilding());
	System.out.println("Job Name: " + lastFailedBuild.getFullDisplayName());
	System.out.println("Job Desciption: " + lastFailedBuild.getDescription());
	System.out.println("Job Result: " + lastFailedBuild.getResult());
	System.out.println("Job URL: " + lastFailedBuild.getUrl());
	System.out.println();
	System.out.println();
}

    
    @Test
    public void getLastStableBuild(){
	System.out.println("Get Last Stable Build Test");
	
	LastStableBuild lastStableBuild  = jenkins.lastStableBuild(jobName);

	System.out.println("Build Number: " + lastStableBuild.getId());	
	System.out.println("Is Building: " + lastStableBuild.getBuilding());
	System.out.println("Job Name: " + lastStableBuild.getFullDisplayName());
	System.out.println("Job Desciption: " + lastStableBuild.getDescription());
	System.out.println("Job Result: " + lastStableBuild.getResult());
	System.out.println("Job URL: " + lastStableBuild.getUrl());
	System.out.println();
	System.out.println();
    }

    @Test
    public void getLastSuccessfulBuild(){
	System.out.println("Get Last Successful Build Test");
	
	LastSuccessfulBuild lastSuccessfulBuild  = jenkins.lastSuccessfulBuild(jobName);

	System.out.println("Build Number: " + lastSuccessfulBuild.getId());	
	System.out.println("Is Building: " + lastSuccessfulBuild.getBuilding());
	System.out.println("Job Name: " + lastSuccessfulBuild.getFullDisplayName());
	System.out.println("Job Desciption: " + lastSuccessfulBuild.getDescription());
	System.out.println("Job Result: " + lastSuccessfulBuild.getResult());
	System.out.println("Job URL: " + lastSuccessfulBuild.getUrl());
	System.out.println();
	System.out.println();
    }

  //  @Test
    public void getLastUnstableBuild(){
	System.out.println("Get Last Unstable Build Test");
	
	LastUnstableBuild lastUnstableBuild  = jenkins.lastUnstableBuild(jobName);

	System.out.println("Build Number: " + lastUnstableBuild.getId());	
	System.out.println("Is Building: " + lastUnstableBuild.getBuilding());
	System.out.println("Job Name: " + lastUnstableBuild.getFullDisplayName());
	System.out.println("Job Desciption: " + lastUnstableBuild.getDescription());
	System.out.println("Job Result: " + lastUnstableBuild.getResult());
	System.out.println("Job URL: " + lastUnstableBuild.getUrl());
	System.out.println();
	System.out.println();
    }

//    @Test
    public void getLastUnsuccessfulBuild(){
	System.out.println("Get Last Unsuccessful Build Test");
	
	LastUnsuccessfulBuild lastUnsuccessfulBuild  = jenkins.lastUnsuccessfulBuild(jobName);

	System.out.println("Build Number: " + lastUnsuccessfulBuild.getId());	
	System.out.println("Is Building: " + lastUnsuccessfulBuild.getBuilding());
	System.out.println("Job Name: " + lastUnsuccessfulBuild.getFullDisplayName());
	System.out.println("Job Desciption: " + lastUnsuccessfulBuild.getDescription());
	System.out.println("Job Result: " + lastUnsuccessfulBuild.getResult());
	System.out.println("Job URL: " + lastUnsuccessfulBuild.getUrl());
	System.out.println();
	System.out.println();
    }
}
