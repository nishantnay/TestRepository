package com.jenkins;

import com.jenkins.methods.LastBuild;
import com.jenkins.methods.LastCompletedBuild;
import com.jenkins.methods.LastFailedBuild;
import com.jenkins.methods.LastStableBuild;
import com.jenkins.methods.LastSuccessfulBuild;
import com.jenkins.methods.LastUnstableBuild;
import com.jenkins.methods.LastUnsuccessfulBuild;
import com.orasi.api.restServices.core.RestService;

public class JenkinsClient {
	private String host = "localhost";
	private String port = "80";
	private String jobPath = "job/";
	private String apiJson = "/api/json";

	public String getBaseUrl() {
		return "http://" + getHost() + ":" + getPort() + "/";
	}
	// private void setBaseUrl(String url) {this.baseUrl = url;}

	public String getHost() {
		return host;
	}

	private void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	private void setPort(String port) {
		this.port = port;
	}

	public String getJobPath() {
		return jobPath;
	}

	private void setJobPath(String jobsPath) {
		this.jobPath = jobsPath;
	}

	public String getJobsURL() {
		return this.getBaseUrl() + this.getJobPath();
	}

	private RestService restService = new RestService();

	public JenkinsClient(String host, String port) {
		setHost(host);
		setPort(port);
	}

	public JenkinsClient(String host, String port, String jobPath) {
		setHost(host);
		setPort(port);
		setJobPath(jobPath);
	}

	public LastBuild lastBuild(String job) {
		LastBuild lastBuild = null;

		restService.sendGetRequest(getJobsURL() + job + "/lastBuild" + apiJson);
		lastBuild = restService.mapJSONToObject(LastBuild.class);

		return lastBuild;
	}

	public LastCompletedBuild lastCompletedBuild(String job) {
		LastCompletedBuild lastCompletedBuild = null;

		restService.sendGetRequest(getJobsURL() + job + "/lastCompletedBuild" + apiJson);
		lastCompletedBuild = restService.mapJSONToObject(LastCompletedBuild.class);
		return lastCompletedBuild;
	}

	public LastFailedBuild lastFailedBuild(String job) {
		LastFailedBuild lastFailedBuild = null;
		restService.sendGetRequest(getJobsURL() + job + "/lastFailedBuild" + apiJson);
		lastFailedBuild = restService.mapJSONToObject(LastFailedBuild.class);
		return lastFailedBuild;
	}

	public LastStableBuild lastStableBuild(String job) {
		LastStableBuild lastStableBuild = null;
		restService.sendGetRequest(getJobsURL() + job + "/lastStableBuild" + apiJson);
		lastStableBuild = restService.mapJSONToObject(LastStableBuild.class);
		return lastStableBuild;
	}

	public LastSuccessfulBuild lastSuccessfulBuild(String job) {
		LastSuccessfulBuild lastSuccessfulBuild = null;
		restService.sendGetRequest(getJobsURL() + job + "/lastSuccessfulBuild" + apiJson);
		lastSuccessfulBuild = restService.mapJSONToObject(LastSuccessfulBuild.class);
		return lastSuccessfulBuild;
	}

	public LastUnstableBuild lastUnstableBuild(String job) {
		LastUnstableBuild lastUnstableBuild = null;
		restService.sendGetRequest(getJobsURL() + job + "/lastUnstableBuild" + apiJson);
		lastUnstableBuild = restService.mapJSONToObject(LastUnstableBuild.class);
		return lastUnstableBuild;
	}

	public LastUnsuccessfulBuild lastUnsuccessfulBuild(String job) {
		LastUnsuccessfulBuild lastUnsuccessfulBuild = null;
		restService.sendGetRequest(getJobsURL() + job + "/lastUnstableBuild" + apiJson);
		lastUnsuccessfulBuild = restService.mapJSONToObject(LastUnsuccessfulBuild.class);
		return lastUnsuccessfulBuild;
	}
}
