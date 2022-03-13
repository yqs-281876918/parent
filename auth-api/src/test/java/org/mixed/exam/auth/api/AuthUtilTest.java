package org.mixed.exam.auth.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthUtilTest {

    @Test
    void parseUsername()
    {
        System.out.println(AuthUtil.parseUsername("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDcxODE4OTIsInVzZXJfbmFtZSI6InlxcyIsImF1dGhvcml0aWVzIjpbIlJPTEVfYWRtIl0sImp0aSI6IjJlNDBkMzM3LWFiZDktNGM3OS1iZTYzLWQ4MzI5NWY0NGY2ZiIsImNsaWVudF9pZCI6ImFsbC1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.JefWfF01XP5Mxr8TN8rfihKd8zZSnFiAyqOkCb9C_Ro"));
    }
}