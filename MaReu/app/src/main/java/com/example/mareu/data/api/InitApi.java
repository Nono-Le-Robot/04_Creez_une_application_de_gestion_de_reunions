package com.example.mareu.data.api;

public class InitApi {
        private static final MeetingApiService service = new FakeMeetingApiService();
        public static MeetingApiService getMeetingApiService() {
            return service;
        }
}
