package com.lq.lqdotcom;

public class ReservationParameters {

	String firstName;
	String lastName;
	String mobileNumber;
	String emailId;
	String zipCode;
	String roomType;
	String hotelName;
	String rateType;
	String payMode;
	String checkInFromNow;
	String stayDays;
	String guestCount;
	String reservationNumber;

	public void setReservationParameters(String []reservationData){
		int index=0;
		for(String s:reservationData){
			index++;
			setData(index, s);
		}
		
	}
	private void setData(int index, String data){
		switch(index){
		case 1:
			setFirstName(data);
			break;
		case 2:
			setLastName(data);
			break;
		case 3:
			setRoomType(data);
			break;
		case 4:
			setHotelName(data);
			break;
		case 5:
			setRateType(data);
			break;
		case 6:
			setPayMode(data);
			break;
		case 7:
			setCheckInFromNow(data);
			break;
		case 8:
			setStayDays(data);
			break;
		case 9:
			setGuestCount(data);
			break;
		case 10:
			setMobileNumber(data);
			
			break;
		case 11:
			setEmailId(data);
			break;
		case 12:
			setZipCode(data);
			break;
		case 13:
			setReservationNumber(data);
			break;
		default:
			break;
		}
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getCheckInFromNow() {
		return checkInFromNow;
	}
	public void setCheckInFromNow(String checkInFromNow) {
		this.checkInFromNow = checkInFromNow;
	}
	public String getStayDays() {
		return stayDays;
	}
	public void setStayDays(String stayDays) {
		this.stayDays = stayDays;
	}
	public String getGuestCount() {
		return guestCount;
	}
	public void setGuestCount(String guestCount) {
		this.guestCount = guestCount;
	}
	public String getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(String reservationNumber) {
		String actualreservationNumber= reservationNumber.substring(1);
		this.reservationNumber = actualreservationNumber;
	}
}
