/*******************************************************************************
 * Copyright (C) 2018 Université de Lille - Inria
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.univLille.cristal.shex.runningTests;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class TestResultForTestReport {
	String name;
	boolean passed; // should be "passed" or "failed"
	String description;
	String testType;
	
	public TestResultForTestReport(String name, boolean passed, String description, String testType) {
		this.name = name;
		this.passed = passed;
		this.description = description;
		this.testType = testType;
	}
}
