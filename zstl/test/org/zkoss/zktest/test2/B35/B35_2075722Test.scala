/* B35_2075722Test.scala

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Jan 7, 2012 12:00:00 AM , Created by Fernando Selvatici
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.zktest.test2.B35

import org.junit.Test
import org.zkoss.zstl.ZTL4ScalaTestCase
import org.zkoss.ztl.unit.JQuery
import org.zkoss.ztl.annotation.Tags

/**
  * @author Fernando Selvatici
  *
  */
@Tags(tags = "B35-2075722.zul,B,E,Window,Button")
class B35_2075722Test extends ZTL4ScalaTestCase {
  @Test
  def testClick() = {
    val zscript =
      """
      <window title="Grid with Group feature" border="normal">
        <html><![CDATA[
Test Drag-Drop on each panel(The following steps should be true.)<br/>
1.Click the first button, and test the drag-drop on each panel (Please hover mouse over the title of the panel)<br/>
2.Click the second button, and test the drag-drop on each panel (Please hover mouse over the title of the panel)<br/>
3.Click the third button, and test the drag-drop on each panel (Please hover mouse over the title of the panel)<br/>
]]></html>
        <zscript>
          <![CDATA[//@IMPORT
import org.zkoss.zkmax.zul.*;
]]>
          <![CDATA[//@DECLARATION
Portalchildren pc1;
Portalchildren pc2;
List panels = new ArrayList();
void addPortalChidren(){
pc1 = new Portalchildren();
pc2 = new Portalchildren();
pc1.setWidth("60%");
pc2.setWidth("40%");
pc1.setParent(pl);
pc2.setParent(pl);
}

void addPaneltoprotal(Portalchildren pc){
	newPanel(pc);
	newPanel(pc);
	newPanel(pc);
}

void include(Component parent,String name){
}

int count=1;
Panelchildren newPanel(Component parent){
Panel panel = new Panel();
panel.setTitle("panel"+count++);
panel.setHeight("50px");
Panelchildren pc = new Panelchildren();
pc.setParent(panel);
panel.setParent(parent);
panels.add(panel);
return pc;
}
void moveLastPanel(){
if(panels.size()>0){
Panel panel = (Panel)panels.get(panels.size()-1);
if(panel.getParent()==pc1){
panel.setParent(pc2);
}else{
panel.setParent(pc1);
}
}
}

void removeLastPanel(){
if(panels.size()>0){
panels.get(panels.size()-1).detach();
panels.remove(panels.size()-1);
}
}
]]>
          <![CDATA[

]]>
        </zscript>
        <div>
          <button label="1.Add Panels" onClick="addPaneltoprotal(pc1)"/>
          <button label="2.Add Panels" onClick="addPaneltoprotal(pc2)"/>
          <button label="3.move Panel" onClick="moveLastPanel()"/>
          <portallayout id="pl" onCreate="addPortalChidren();">
          </portallayout>
        </div>
      </window>
    """
    runZTL(zscript, () => {
      var topPanel1, topPanel2, topPanel3, topPanel4, topPanel5, topPanel6 = 0;

      // Click on first button
      click(jq("@button"));
      waitResponse();

      mouseOver(jq(".z-panel-header"));
      waitResponse();

      val panelheader1 = jq(".z-panel-header:contains(panel1)")
      val panelheader2 = jq(".z-panel-header:contains(panel2)")
      val panelheader3 = jq(".z-panel-header:contains(panel3)")
      val panelheader4 = jq(".z-panel-header:contains(panel4)")
      val panelheader5 = jq(".z-panel-header:contains(panel5)")
      val panelheader6 = jq(".z-panel-header:contains(panel6)")
      val panel1 = jq(".z-panel:contains(panel1)")
      val panel2 = jq(".z-panel:contains(panel2)")
      val panel3 = jq(".z-panel:contains(panel3)")
      val panel4 = jq(".z-panel:contains(panel4)")
      val panel5 = jq(".z-panel:contains(panel5)")
      val panel6 = jq(".z-panel:contains(panel6)")

      dragdropToObject(panelheader3, panelheader1, "2,2", "0,0")
      waitResponse();

      topPanel3 = panel3.positionTop();
      topPanel1 = panel1.positionTop();

      verifyTrue("The panel 1 should be below the panel 3", topPanel1 > topPanel3);

      dragdropToObject(panelheader2, panelheader3, "2,2", "0,0");
      waitResponse();

      topPanel2 = panel2.positionTop();
      topPanel3 = panel3.positionTop();

      verifyTrue("The panel 3 should be below the panel 2", topPanel3 > topPanel2);

      dragdropToObject(panelheader1, panelheader2, "2,2", "0,0");
      waitResponse();

      topPanel1 = panel1.positionTop();
      topPanel2 = panel2.positionTop();

      verifyTrue("The panel 2 should be below the panel 1", topPanel2 > topPanel1)

      // Click on second button
      click(jq("@button").get(1));
      waitResponse();

      dragdropToObject(panelheader6, panelheader4, "2,2", "0,0");
      waitResponse();

      topPanel6 = panel6.positionTop();
      topPanel4 = panel4.positionTop();

      verifyTrue("The panel 4 should be below the panel 6", topPanel4 > topPanel6);

      dragdropToObject(panelheader5, panelheader6, "2,2", "0,0");
      waitResponse();

      topPanel5 = panel5.positionTop();
      topPanel6 = panel6.positionTop();

      verifyTrue("The panel 6 should be below the panel 5", topPanel6 > topPanel5);

      dragdropToObject(panelheader4, panelheader5, "2,2", "0,0");
      waitResponse();

      topPanel4 = panel4.positionTop();
      topPanel5 = panel5.positionTop();

      verifyTrue("The panel 5 should be below the panel 4", topPanel5 > topPanel4)

      // Click on third button
      click(jq("@button").get(2));
      waitResponse();

      dragdropToObject(panelheader6, panelheader3, "2,2", "0,0");
      waitResponse();

      dragdropToObject(panelheader6, panelheader1, "2,2", "0,0");
      waitResponse();

      topPanel6 = panel6.positionTop();
      topPanel1 = panel1.positionTop();

      verifyTrue("The panel 1 should be below the panel 6", topPanel1 > topPanel6);

      dragdropToObject(panelheader1, panelheader6, "2,2",  "0,0");
      waitResponse();

      topPanel1 = panel1.positionTop();
      topPanel6 = panel6.positionTop();

      verifyTrue("The panel 6 should be below the panel 1", topPanel6 > topPanel1);

    })
  }
}