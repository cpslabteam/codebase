/*
 * Created on 13/Jul/2004
 */
package codebase.iterators;

import junit.framework.Assert;
import junit.framework.TestCase;
import codebase.monitors.CounterMonitor;

/**
 * @author André Gonçalves
 */
public class TestLineCount
        extends TestCase {
    
    public void testLineCountIntegration() {
        CounterMonitor lineCounter = new CounterMonitor();
        
        MonitoredIterator lnc = new MonitoredIterator(new Enumerator(10),
            lineCounter);
        
        Assert.assertEquals(lineCounter.count(), 0);
        lnc.next();
        Assert.assertEquals(lineCounter.count(), 1);
        lnc.next();
        Assert.assertEquals(lineCounter.count(), 2);
        
        while (lnc.hasNext()) {
            lnc.next();
        }        
        Assert.assertEquals(lineCounter.count(), 10);
    }
    
}
