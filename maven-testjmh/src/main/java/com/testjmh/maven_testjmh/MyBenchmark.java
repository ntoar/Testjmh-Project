/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.testjmh.maven_testjmh;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.doglib.Dog;
import com.doglib.DogLib;

@State(Scope.Benchmark)
public class MyBenchmark {
	
	//private DogLib dl = new DogLib("src\\main\\resources\\dogs.xml");
	private DogLib dl;
	
	@Param({"10","100","1000","10000"})
	private int LISTSIZE;
	
	private String name [] = {"James","Lars","Kirk","Robert"};
	private String date [] = {"10-01-2010","10-02-2010","10-01-2012","10-02-2012"};
	private double weight [] = {10,15,20,25};
	private String breed [] = {"German Shepherd","Siberian Husky","Shiba Inu","Labrador Retriever"};

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations=5)
    @Measurement(iterations=5)
    public void testMethod(Blackhole bh) {
            	
    	Map<String,Double> m =dl.averageWeightPerBreed();
    	bh.consume(m.size());
    }
    
    
    @Setup
    public void setup() {
    	this.dl=new DogLib();
    	createList(dl);
    }
    
    public static void main(String[] args) throws RunnerException {
    	Options opt = new OptionsBuilder().include(MyBenchmark.class.getSimpleName()).forks(1).build();
    	new Runner(opt).run();
    }
    
    private void createList(DogLib dl) {    	
    	for(int i=0; i<LISTSIZE;i++) {
    		
    		int randnum1=ThreadLocalRandom.current().nextInt(0,4);
    		int randnum2=ThreadLocalRandom.current().nextInt(0,4);
    		
    		Dog d = new Dog(name[randnum1],date[randnum2],weight[randnum1],breed[randnum2]);
    		dl.addDog(d);
    	}
    	
    }

}
