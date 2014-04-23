package org.zkoss.zktest.test2.B70

import org.zkoss.ztl.Tags
import org.zkoss.zstl.ZTL4ScalaTestCase
import org.junit.Test

@Tags(tags = "B70-ZK-2217.zul")
class B70_ZK_2217Test extends ZTL4ScalaTestCase {

@Test
def testClick() = {
  val zscript = """<?xml version="1.0" encoding="UTF-8"?>
<zk xmlns="http://www.zkoss.org/2005/zul" xmlns:n="native">
	<div vflex="true" width="100%" apply="org.zkoss.bind.BindComposer"
		viewModel="@id('vm') @init('org.zkoss.zktest.test2.B70_ZK_2217_ViewModel')">
		<borderlayout id="BLContenedor" width="100%"
			style="height:100%">
			<west width="380px" splittable="true" collapsible="true"
				style="border-left:0px" open="true" autoscroll="true" />
			<center>
				<div hflex="true" vflex="true">
					<div>1. should not see js error in js console</div>
					<div>2. click 'load data'</div>
					<div>3. the footer and paging should not hide</div>
					<listbox id="LB" mold="paging" autopaging="true"
						vflex="true" sizedByContent="true" width="99%" span="0"
						model="@bind(vm.data)">

						<listhead sizable="true">
							<listheader label="Listheader 1" />
							<listheader label="Listheader 2" />
							<listheader label="Listheader 3" />
							<listheader label="Listheader 4" />
							<listheader label="Listheader 5" />
							<listheader label="Listheader 6" />
							<listheader label="Listheader 7" />
							<listheader label="Listheader 8" />
						</listhead>
						<auxhead id="auxh" visible="false">
							<auxheader align="center" colspan="2"
								label="Auxheader 1" />
							<auxheader align="center" colspan="2"
								label="Auxheader 2" />
							<auxheader align="center" colspan="2"
								label="Auxheader 3" />
							<auxheader align="center" colspan="2"
								label="Auxheader 4" />

						</auxhead>
						<template name="model" var="line">
							<listitem children="@bind(line)">
								<template name="children" var="cell">
									<listcell label="@load(cell)"
										style="text-align: center" />
								</template>
							</listitem>
						</template>
						<listfoot>
							<listfooter label="LF" />
						</listfoot>
					</listbox>
					<button label="Load data"
						onClick="@command('loadData')" />
				</div>
			</center>
		</borderlayout>
	</div>
</zk>"""  
  runZTL(zscript,
    () => {
      
      click(jq(".z-button"))
      waitResponse()
      
      val lb = jq(".z-listbox").toWidget()
      verifyTrue("the footer and paging should not hide", jq(lb.$n("foot")).isVisible() && jq(lb.$n("pgib")).isVisible())
    })
    
  }
}