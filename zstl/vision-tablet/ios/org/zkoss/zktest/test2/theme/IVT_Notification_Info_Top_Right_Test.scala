package org.zkoss.zktest.test2.theme

import org.zkoss.zstl.ZTL4ScalaTestCase
import org.zkoss.ztl.annotation.Tags

@Tags(tags = "IOS,VisionTest")
class IVT_Notification_Info_Top_Right_Test extends ZTL4ScalaTestCase {
	def testClick() = {
		val zscript = """
<zk>
	<zscript>
	<![CDATA[
	import org.zkoss.zk.ui.util.Clients;
	import org.zkoss.zk.ui.Component;
	
	String msg = "Use the force, Harry.";
	String type = Clients.NOTIFICATION_TYPE_INFO;
	Component ref = null;
	String pval = "top_right";
	int dur = -1;
	boolean closable = false;
	
	Clients.showNotification(msg, type, ref, pval, dur, closable);
	]]>
	</zscript>
</zk>
		""";

		runZTL(zscript,
			() => {
				verifyImage();
			});
	}
}
