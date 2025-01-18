package home.iot;

import java.util.regex.Pattern;


public class Consts {
	
	public class R7_5800X3D {
		public static final Pattern CPU_TCTL = Pattern.compile("Tctl:[^+]*\\+(\\d+\\.\\d+)°C");
	}

	public class I7_8700K {
		public static final Pattern CPU_TCTL = Pattern.compile("Package id 0:[^+]*\\+(\\d+\\.\\d+)°C");
	}

	public class X570S {
		public static final String COMMAND_GPU = "sensors amdgpu-pci-0d00";
		public static final String COMMAND_CPU = "sensors k10temp-pci-00c3";
		public static final String COMMAND_NVME0 = "sensors nvme-pci-0100";
		public static final String COMMAND_NVME1 = "sensors nvme-pci-0400";
	}

	public class B550 {
		public static final String COMMAND_GPU = "sensors amdgpu-pci-0900";
		public static final String COMMAND_CPU = "sensors k10temp-pci-00c3";
		public static final String COMMAND_NVME0 = "sensors nvme-pci-0400"; // 1TO
		public static final String COMMAND_NVME1 = "sensors nvme-pci-0100"; // 500go
	}

	public class MSI_B550 {
		public static final String COMMAND_GPU = "sensors amdgpu-pci-2d00";
		public static final String COMMAND_CPU = "sensors k10temp-pci-00c3";
		public static final String COMMAND_NVME0 = "sensors nvme-pci-0400"; // 1TO
		public static final String COMMAND_NVME1 = "sensors nvme-pci-0100"; // 500go
	}
	
	public class Z390 {
		public static final String COMMAND_GPU = "sensors amdgpu-pci-0300";
		public static final String COMMAND_CPU = "sensors coretemp-isa-0000"; 
		public static final String COMMAND_NVME0 = "sensors nvme-pci-0600"; // 1 to
		public static final String COMMAND_NVME1 = "sensors nvme-pci-0400"; // 500 go
	}

	public static final int CPU_CAPTOR = 111;
	public static final int GPU_CAPTOR = 110;
	public static final int NVME0_CAPTOR = 112;
	public static final int NVME1_CAPTOR = 113;
	public static final Pattern CPU_TCTL = R7_5800X3D.CPU_TCTL;
	public static final Pattern GPU_JUNCTION = Pattern.compile("junction:[^+]*\\+(\\d+\\.\\d+)°C");
	public static final Pattern COMPOSITE = Pattern.compile("Composite:[^+]*\\+(\\d+\\.\\d+)°C");
	public static final String COMMAND_GPU = MSI_B550.COMMAND_GPU;
	public static final String COMMAND_CPU = MSI_B550.COMMAND_CPU;
	public static final String COMMAND_NVME0 = MSI_B550.COMMAND_NVME0;
	public static final String COMMAND_NVME1 = MSI_B550.COMMAND_NVME1;
	public static final String HOST = "http://homeiot/captor/";

	
	
}
