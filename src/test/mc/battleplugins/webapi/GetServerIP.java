/**
 *
 * @author lDucks
 *
 */
package test.mc.battleplugins.webapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

import mc.battleplugins.webapi.controllers.timers.Scheduler;
import mc.battleplugins.webapi.object.WebURL;
import mc.battleplugins.webapi.object.callbacks.URLResponseHandler;

import org.bukkit.Bukkit;

/**
 * @author lDucks
 *
 */
public class GetServerIP {
	
	static String serverip = null;
	
	public static String getServerIP(){
		if(serverip == null) {
			URL whatismyip;
			try {
				whatismyip = new URL("http://BattlePunishments.net/grabbers/ip.php");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			WebURL url = new WebURL(whatismyip);

			url.getPage(new URLResponseHandler() {
				public void validResponse(final BufferedReader br) throws IOException {
					final String ip = br.readLine();

					if(ip == null)
						throw new NullPointerException();

					Scheduler.scheduleSynchrounousTask(new Runnable() {
						public void run() {
							String newip = ip;
							if(Bukkit.getPort() != 25565) {
								newip = newip+":"+Bukkit.getPort();
							}

							serverip = newip;
						}

					});
				}

				public void invalidResponse(Exception e) {
					e.printStackTrace();
				}
			});
		}

		return serverip;
	}
}