package com.example.softwareconstructionassign;

import static org.junit.Assert.*;
import com.example.softwareconstructionassign.stockNews;
import org.junit.Test;

public class stockNewsJUnitTest {


    stockNews sc = new stockNews();

    @Test
    public void fillDropdownListTest(){
        assertEquals(false, sc.fillDropdownList(null,null) );
    }

    @Test
    public void fillListViewTest(){
        assertEquals(false, sc.fillListView(null,null) );
    }

    @Test
    public void fillChartTest(){
        assertEquals(false, sc.fillChart(null) );
    }
}
