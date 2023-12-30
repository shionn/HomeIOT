package home.iot;

import java.util.regex.Pattern;

public class Consts {

	public static final int CPU_CAPTOR = 111;
	public static final int GPU_CAPTOR = 110;
	public static final Pattern GPU_JUNCTION = Pattern.compile("junction:[^+]*\\+(\\d+\\.\\d+)°C");
	public static final Pattern CPU_TCTL = Pattern.compile("Tctl:[^+]*\\+(\\d+\\.\\d+)°C");
	public static final String COMMAND = "sensors amdgpu-pci-0d00 k10temp-pci-00c3";
	public static final String HOST = "http://homeiot/captor/";

}
