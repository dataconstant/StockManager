package com.example.softwareconstructionassign;

import static org.junit.Assert.*;

import com.example.softwareconstructionassign.mainscreen;
import org.junit.Test;

public class DashBoardTest {

    mainscreen m = new mainscreen();

    @Test
    public void checkid(){
        assertEquals("abhishekchetri", m.getID("abhishekchetri@outlook.com"));
    }

}
