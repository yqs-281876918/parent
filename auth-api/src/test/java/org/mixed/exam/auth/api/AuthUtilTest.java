package org.mixed.exam.auth.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthUtilTest {

    @Test
    void parseUsername()
    {
        //System.out.println(AuthUtil.parseUsername("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDc3MTk4MDIsInVzZXJfbmFtZSI6InlxcyIsImF1dGhvcml0aWVzIjpbIlJPTEVfYWRtIl0sImp0aSI6IjQ0NWVhNjJmLWZiYjItNGMwMC1hOTEzLTQyNDJjNWIyMjUyNSIsImNsaWVudF9pZCI6ImFsbC1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.hIxJ0JlFL9QIodYyn_uv8KTeHuOilypVSz08gPn6eqQ"));
        System.out.println(AuthUtil.parseAuthorities("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDc3MTk4MDIsInVzZXJfbmFtZSI6InlxcyIsImF1dGhvcml0aWVzIjpbIlJPTEVfYWRtIl0sImp0aSI6IjQ0NWVhNjJmLWZiYjItNGMwMC1hOTEzLTQyNDJjNWIyMjUyNSIsImNsaWVudF9pZCI6ImFsbC1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.hIxJ0JlFL9QIodYyn_uv8KTeHuOilypVSz08gPn6eqQ"));
        System.out.println(AuthUtil.checkIsExp("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDc3MTk4MDIsInVzZXJfbmFtZSI6InlxcyIsImF1dGhvcml0aWVzIjpbIlJPTEVfYWRtIl0sImp0aSI6IjQ0NWVhNjJmLWZiYjItNGMwMC1hOTEzLTQyNDJjNWIyMjUyNSIsImNsaWVudF9pZCI6ImFsbC1jbGllbnQiLCJzY29wZSI6WyJhbGwiXX0.hIxJ0JlFL9QIodYyn_uv8KTeHuOilypVSz08gPn6eqQ"));
    }
}