package com.jenkins.methods;

public class LastStableBuild {
    private String buildable;
    private String building;
    private String description;
    private String duration;
    private String fullDisplayName;	
    private String url;
    private String nextBuildNumber;
    private String result;
    private String id;
	
    public String getBuildable() {return buildable;}
    public String getBuilding() {return building;}
    public String getId() {return id;}
    public String getDescription() {return description;}
    public String getDuration() {return duration;}
    public String getFullDisplayName() {return fullDisplayName;}
    public String getNextBuildNumber() {return nextBuildNumber;}
    public String getResult(){return result;}
    public String getUrl() {return url;}
}
