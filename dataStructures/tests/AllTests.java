package dataStructures.tests;

import org.junit.platform.suite.api.SelectClasses; 
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({ SepChainHashTableTest.class, BSTreeTest.class, SinglyLinkedListTest.class, SetLinkedListTest.class})
public class AllTests {

}
