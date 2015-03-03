package com.github.farhan3.jclickerquiz.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.github.farhan3.jclickerquiz.model.DataUtilTest;
import com.github.farhan3.jclickerquiz.model.QuestionTest;

@RunWith(Suite.class)
@SuiteClasses({
	DataUtilTest.class,
	QuestionTest.class,
})
public class AllTests {

}
