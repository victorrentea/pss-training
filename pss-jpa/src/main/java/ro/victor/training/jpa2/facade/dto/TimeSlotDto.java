package ro.victor.training.jpa2.facade.dto;

import java.time.DayOfWeek;

import ro.victor.training.jpa2.domain.entity.TimeSlot;

public class TimeSlotDto {
	public DayOfWeek day;
	public int startHour;
	public int durationInHours;
	public String roomId;
	
	public TimeSlotDto(TimeSlot timeSlot) {
		this(timeSlot.getDay(), timeSlot.getStartHour(), timeSlot.getDurationInHours(), timeSlot.getRoomId());
	}
	public TimeSlotDto(DayOfWeek day, int startHour, int durationInHours, String roomId) {
		this.day = day;
		this.startHour = startHour;
		this.durationInHours = durationInHours;
		this.roomId = roomId;
	}
	
}
