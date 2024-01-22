package com.pluralsight.springcloudconfigserver;

public record GetUserResponse(String username, String email, String firstName, String lastName) {
}