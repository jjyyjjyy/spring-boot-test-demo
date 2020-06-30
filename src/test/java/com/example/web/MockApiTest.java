package com.example.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author jy
 */
@ExtendWith(MockitoExtension.class)
class MockApiTest {

    @Mock
    private List<String> mockedList;

    @Spy
    @InjectMocks
    private ListHolder listHolder;

    @Test
    void testVerifyInvocation() {
        List<String> mockedList = mock(List.class);
        mockedList.add("aaa");
        mockedList.clear();

        // 验证是否调用过指定方法
        verify(mockedList).add("aaa");
        verify(mockedList).clear();
    }

    @Test
    void testStub() {
        LinkedList<Integer> mockedList = mock(LinkedList.class);
        // stub返回值
        when(mockedList.get(0)).thenReturn(1000);
        when(mockedList.get(999)).thenReturn(10000);
        // stub异常
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        assertEquals(1000, mockedList.get(0));
        assertEquals(10000, mockedList.get(999));
        Assertions.assertThrows(RuntimeException.class, () -> mockedList.get(1));

        when(mockedList.get(anyInt())).thenReturn(123);
        assertEquals(123, mockedList.get(1234567));
    }

    @Test
    void testInvocationTimes() {
        List<String> mockedList = mock(List.class);
        mockedList.add("once");
        mockedList.add("twice");
        mockedList.add("twice");
        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        // 验证方法调用次数
        verify(mockedList, times(1)).add("once");
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");
        verify(mockedList, never()).add("never happens");

        List<String> mock = mock(List.class);
        verifyNoInteractions(mock);
        verifyNoMoreInteractions(mock);

        mock.add("1");
        Assertions.assertThrows(NoInteractionsWanted.class, () -> verifyNoInteractions(mock));
        Assertions.assertThrows(NoInteractionsWanted.class, () -> verifyNoMoreInteractions(mock));
    }

    @Test
    void testVerifyOrder() {
        List<String> mockedList = mock(List.class);
        mockedList.add("first");
        mockedList.add("second");
        // 验证单个对象的方法调用顺序
        InOrder inOrder = inOrder(mockedList);
        inOrder.verify(mockedList).add("first");
        inOrder.verify(mockedList).add("second");

        List<String> firstMockedList = mock(List.class);
        List<String> secondMockedList = mock(List.class);
        firstMockedList.add("first");
        secondMockedList.add("second");
        // 调用多个对象的方法调用顺序
        InOrder inOrder2 = inOrder(firstMockedList, secondMockedList);
        inOrder2.verify(firstMockedList).add("first");
        inOrder2.verify(secondMockedList).add("second");
    }

    @Test
    void testConsecutiveCall() {
        List<String> mockedList = mock(List.class);
        // 调用方法多次后, 依次返回one,two,three,three...
        when(mockedList.get(anyInt())).thenReturn("one", "two", "three");
        assertEquals("one", mockedList.get(0));
        assertEquals("two", mockedList.get(0));
        assertEquals("three", mockedList.get(0));
        assertEquals("three", mockedList.get(0));
    }

    @Test
    void testStubAnswer() {
        List<String> mockedList = mock(List.class);
        // stub返回Answer对象
        when(mockedList.get(anyInt())).thenAnswer(invocation -> "List::get: " + invocation.getArgument(0));

        assertEquals("List::get: 111", mockedList.get(111));
    }

    @Test
    void testSpy() {
        List<String> list = spy(new LinkedList<>());

        // spy会先调用真正的方法, 然后stub
        when(list.size()).thenReturn(200);
        assertEquals(200, list.size());
        doReturn("abc").when(list).get(0);
        assertEquals("abc", list.get(0));

        // 数组越界异常, 因为调用了真正的list.get(1)
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> when(list.get(1)).thenReturn("a"));
    }

    @Test
    void testGlobalAnswer() {
        List<String> mockedList = mock(List.class, RETURNS_SMART_NULLS);
        assertNull(mockedList.get(1));
        assertEquals(0, mockedList.size());
        assertFalse(mockedList.isEmpty());
    }

    @Test
    void testArgumentCapture() {

        List<String> mockedList = mock(List.class);
        mockedList.add("wow");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(mockedList).add(captor.capture());

        assertEquals("wow", captor.getValue());

    }

    @Test
    void testReset() {
        List<String> mockedList = mock(List.class);
        when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());

        reset(mockedList);
        assertEquals(0, mockedList.size());
    }

    @Test
    void testBDDMockito() {
        List<String> mockedList = mock(List.class);
        // given
        BDDMockito.given(mockedList.size()).willReturn(100);
        // when
        int size = mockedList.size();
        // then
        assertEquals(100, size);
    }

    @Test
    void testMockAnnotation() {
        when(mockedList.size()).thenReturn(1000);
        assertEquals(1000, mockedList.size());
        assertEquals(1000, listHolder.getSize());
    }

    @Test
    void testMockDetails() {
        assertTrue(mockingDetails(mockedList).isMock());
        assertFalse(mockingDetails(mockedList).isSpy());
    }

    @Test
    void testMockAbstractClass() {
        AbstractFoo abstractFoo = spy(AbstractFoo.class);
        when(abstractFoo.size0()).thenReturn(100);
        assertEquals(100, abstractFoo.size0());
        assertEquals(100, abstractFoo.size());
    }

    @Test
    void testArgumentMatch() {
        when(mockedList.add(argThat("a"::equals))).thenReturn(true);
        when(mockedList.add(argThat("b"::equals))).thenReturn(false);
        assertTrue(mockedList.add("a"));
        assertFalse(mockedList.add("b"));
    }

    public static abstract class AbstractFoo {

        public int size() {
            return size0();
        }

        protected abstract int size0();
    }

    public static class ListHolder {
        private List<String> list;

        public void setList(List<String> list) {
            this.list = list;
        }

        public int getSize() {
            return list.size();
        }
    }
}
