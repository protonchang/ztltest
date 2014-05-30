/**
 *
 */
package org.zkoss.zstl
import org.zkoss.ztl.ZKClientTestCase
import org.zkoss.ztl.util.ConfigHelper
import org.zkoss.ztl.Widget
import scala.collection.JavaConversions._
import com.thoughtworks.selenium.SeleniumException
import org.zkoss.ztl.util.ZKSelenium
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.ArrayList
import java.util.concurrent.Callable
import java.util.HashSet
import org.zkoss.ztl.ConnectionManager
import org.apache.commons.exec.CommandLine
import org.apache.commons.exec.DefaultExecutor
import java.util.Date

/**
 * ZTL for Scala to test
 * @author jumperchen
 */
class ZTL4ScalaTestCase extends ZKClientTestCase {
  var ch = ConfigHelper.getInstance()
  target = ch.getServer() + ch.getContextPath() + "/" + ch.getAction()
  browsers = getBrowsers(ch.getBrowser())
  _timeout = ch.getTimeout().toInt
  caseID = getClass().getSimpleName()
  val _engine = new ThreadLocal[Widget]();
  
  def runZTL(zscript: String, executor: () => Unit) {
    val luuid = new Date().getTime();
    println(getTimeUUID() + "-" + luuid + ":log 1");
    val executorService = Executors.newCachedThreadPool();
    val browserSet = new HashSet[String];
        
    for (browser <- browsers) { 
      
      println(getTimeUUID() + "-" + luuid + ":log 2");
      
      val zkSelenium = browser.asInstanceOf[ZKSelenium];
      browserSet.add(zkSelenium.getBrowserName());
      println("add browser: " + zkSelenium.getBrowserName());

      executorService.execute(new Runnable() {
        def run() = {
          println(getTimeUUID() + "-" + luuid + ":log 3");
          try {
            println(getTimeUUID() + "-" + luuid + ":log 4-1");
            start(browser);
            windowFocus();
            windowMaximize();
            _engine.set(new Widget(new StringBuffer("zk.Desktop._dt")));
            println(getTimeUUID() + "-" + luuid + ":log 4-2");
            if (!zscript.isEmpty())
              runRawZscript(
                zscript
                  toString ());
              waitResponse();
	          executor();
	          println(getTimeUUID() + "-" + luuid + ":log 4-3");
			} catch {
				case e : SeleniumException =>
				  	println(getTimeUUID() + "-" + luuid + "selenium exception...");
					ConfigHelper.getInstance().clearCache(zkSelenium);
					zkSelenium.shutdown();
					throw e;
				case other: Throwable =>
				  	println(getTimeUUID() + "-" + luuid +  ":other exception-" + other.getMessage());
				  	other.printStackTrace();
					throw other;
			} finally {
				browserSet.remove(zkSelenium.getBrowserName());
				stop();
				println(getTimeUUID() + "-" + luuid + ":log 4-4");
				println(getTimeUUID() + "-" + luuid + ":log 4-5");
			}
			println(getTimeUUID() + "-" + luuid + ":log 4-6-" + zkSelenium.getBrowserName());
        }
      });
    }
    
    executorService.shutdown();
    try {
    	executorService.awaitTermination(_timeout, TimeUnit.MILLISECONDS);
    } catch {
      case e: InterruptedException => println(getTimeUUID() + "-" + luuid + ":in termination...");
    }
    
    println(getTimeUUID() + "-" + luuid + ":log 5-1");
    
    val iter = browserSet.iterator();
    while (iter.hasNext()) {
      val b = iter.next();
      println("kill thread belong to browser:" + b);
      
      val url = ConnectionManager.getInstance().getOpenedRemote(b);
      
      // get URL means it got block ....
      if(url != null) {
        try {
        	val cl = new CommandLine("/bin/bash");
        	cl.addArgument(ClassLoader.getSystemResource("restartVm.sh").getFile());
        	cl.addArgument(b);
        	cl.addArgument(url.replaceAll(".*//", "").replaceAll(":.*", ""));
        	new DefaultExecutor().execute(cl);
        } catch {
        	case t:Exception => println(t); t.printStackTrace();
        }
      } else
        iter.remove();
    }
    
    println(getTimeUUID() + "-" + luuid + ":log 6");
        
    if(browserSet.size() > 0) Thread.sleep(ch.getRestartSleep());
    
    for(b <- browserSet) {
      ConnectionManager.getInstance().releaseRemote(b);
    }
    
    println(getTimeUUID() + "-" + luuid + ":log 7");
  }
  
  def runRawZscript(zscript: String) {
	runZscript(
		zscript
			trim ()
			replace ("\\", "\\\\")
			replace ("'", "\\'")
			replaceAll ("\r", "")
			replaceAll ("\n", "\\\\n"))
  }
  def runZTL(zscript: scala.xml.Elem, executor: () => Unit){
	runZTL(zscript.toString(),executor);
  }  

  def engine(): Widget = _engine.get();
  def driver() = getWebDriver()
}