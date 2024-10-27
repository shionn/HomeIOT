package home.iot;

import java.util.regex.Pattern;

public class Consts {

	public static final int CPU_CAPTOR = 111;
	public static final int GPU_CAPTOR = 110;
	public static final int NVME0_CAPTOR = 112;
	public static final int NVME1_CAPTOR = 113;
	public static final Pattern GPU_JUNCTION = Pattern.compile("junction:[^+]*\\+(\\d+\\.\\d+)°C");
	public static final Pattern CPU_TCTL = Pattern.compile("Tctl:[^+]*\\+(\\d+\\.\\d+)°C");
	public static final Pattern COMPOSITE = Pattern.compile("Composite:[^+]*\\+(\\d+\\.\\d+)°C");
	public static final String COMMAND = "sensors amdgpu-pci-0d00 k10temp-pci-00c3";
	public static final String COMMAND_GPU = "sensors amdgpu-pci-0d00";
	public static final String COMMAND_CPU = "sensors k10temp-pci-00c3";
	public static final String COMMAND_NVME0 = "sensors nvme-pci-0100";
	public static final String COMMAND_NVME1 = "sensors nvme-pci-0400";
	public static final String HOST = "http://homeiot/captor/";

}
