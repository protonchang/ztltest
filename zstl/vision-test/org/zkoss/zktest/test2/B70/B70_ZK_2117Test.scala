package org.zkoss.zktest.test2.B70

import org.zkoss.ztl.Tags
import org.zkoss.zstl.ZTL4ScalaTestCase
import org.junit.Test

@Tags(tags = "B70-ZK-2117.zul")
class B70_ZK_2117Test extends ZTL4ScalaTestCase {

@Test
def testClick() = {
  val zscript = """<?xml version="1.0" encoding="UTF-8"?>

<!--
B70-ZK-2117.zul

	Purpose:
		
	Description:
		
	History:
		Wed, Feb 19, 2014  2:55:17 PM, Created by jumperchen

Copyright (C)  Potix Corporation. All Rights Reserved.

-->
<zk xmlns:h="native">
	<zscript><![CDATA[
		public void slideOpen() {
			south.invalidate();
			Clients.evalJavaScript("var wgt=zk.Widget.$('" + south.getUuid() + "'); wgt.doClick_(new zk.Event(wgt, 'onClick', {domTarget: wgt.$n('colled')}))");
		}
		]]>
	</zscript>
     <borderlayout vflex="1" hflex="1">
     	<center>
     		<hlayout>
	     		<!-- <button onClick="south.invalidate(); south.setOpen(true);">Click to setOpen(south)</button> -->
	     		<button onClick="slideOpen();">Click to slide open (south), if you should see the south contains all of the yellow area.</button>
     		</hlayout>
     	</center>
         <south id="south" border="normal" title="Container Detail" collapsible="true" open="false" cmargins="0,0,0,0" margins="0,0,0,0" hflex="min">
             <window mode="embedded" border="none" vflex="min" hflex="1" id="edit_FORM_CONTAINER_DETAILS">
                 <div vflex="min" hflex="min" id="FORM_CONTAINER_DETAILS" style="background:yellow">
                 	<label value="label"></label>
                 	<label value="label"></label>
                 	<label value="label"></label>
                 	<label value="label"></label>
                 	<label value="label"></label>
                 </div>
             </window>
         </south>
     </borderlayout>
</zk>"""  
  runZTL(zscript,
    () => {
      click(jq(".z-button"))
      waitResponse()
      verifyImage()
    })
    
  }
}