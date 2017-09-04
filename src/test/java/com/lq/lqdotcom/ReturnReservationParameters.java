package com.lq.lqdotcom;

public class ReturnReservationParameters {

	String userID;
	String lastName;
	String password;
	String roomType;
	String hotelName;
	String rateType;
	String payMode;
	String checkInFromNow;
	String stayDays;
	String guestCount;
	String reservationNumber;

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
			setUserID(data);
			break;
		case 2:
			setLastName(data);
			break;
		case 3:
			setPassword(data);
			break;
		case 4:
			setRoomType(data);
			break;
		case 5:
			setHotelName(data);
			
			break;
		case 6:
			setRateType(data);
			
			break;
		case 7:
			setPayMode(data);
			
			break;
		case 8:
			setCheckInFromNow(data);
			
			break;
		case 9:
			setStayDays(data);
			
			break;
		case 10:
			setGuestCount(data);
			
			break;

		case 11:
			setReservationNumber(data);
			break;
		default:
			break;
		}
	}

}
