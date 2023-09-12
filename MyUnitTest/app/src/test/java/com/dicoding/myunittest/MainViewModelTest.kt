package com.dicoding.myunittest

import org.junit.Before

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.junit.Assert.assertEquals

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var cuboidModel: CuboidModel

    private val dummyLength = 12.0
    private val dummyWidth = 7.0
    private val dummyHeight = 6.0

    private val dummyVolume = 504.0
    private val dummyCircumference = 100.0
    private val dummySurfaceArea = 396.0

    @Before
    fun before(){
        cuboidModel = Mockito.mock(CuboidModel::class.java)
        mainViewModel = MainViewModel(cuboidModel)
    }

//      test with junit
    @Test
    fun testVolume() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth,dummyLength,dummyHeight)
        val volume = mainViewModel.getVolume()
        assertEquals(dummyVolume,volume,0.0001)
    }

    @Test
    fun testCircumference() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val circumference = mainViewModel.getCircumferene()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }
    @Test
    fun testSurfaceArea() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val surfaceArea = mainViewModel.getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }

//testing with mock
@Test
fun testMockVolume() {
    `when`(mainViewModel.getVolume()).thenReturn(dummyVolume)
    val volume = mainViewModel.getVolume()
    verify(cuboidModel).getVolume()
    assertEquals(dummyVolume, volume, 0.0001)
}
    @Test
    fun testMockCircumference() {
        `when`(mainViewModel.getCircumferene()).thenReturn(dummyCircumference)
        val circumference = mainViewModel.getCircumferene()
        verify(cuboidModel).getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }
    @Test
    fun testMockSurfaceArea() {
        `when`(mainViewModel.getSurfaceArea()).thenReturn(dummySurfaceArea)
        val surfaceArea = mainViewModel.getSurfaceArea()
        verify(cuboidModel).getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }


}