package com.example.softwareconstructionassign;

import static org.junit.Assert.*;

import com.example.softwareconstructionassign.mainscreen;
import org.junit.Test;

public class DashBoardTest {

    mainscreen m = new mainscreen();

    @Test
    public void checkid(){
        assertEquals("abhishekchetri", m.getID("abhishekchetri@outlook.com"));
        assertEquals("qwe", m.getID("qwe@gmail.com"));
        assertEquals("123", m.getID("123@outlook.com"));
    }

}
