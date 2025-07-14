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

	public class MSI_B550_5800X3D {
		public static final String COMMAND_GPU = "sensors amdgpu-pci-2d00";
		public static final String COMMAND_CPU = "sensors k10temp-pci-00c3";
		public static final String COMMAND_NVME0 = "sensors nvme-pci-0100"; // 1TO
//		public static final String COMMAND_NVME1 = "sensors nvme-pci-0400"; // 500go
	}

	public class MSI_B550_4750G {
		public static final String COMMAND_GPU = "sensors amdgpu-pci-1200";
		public static final String COMMAND_ONBOARD_GPU = "sensors amdgpu-pci-3000";
		public static final String COMMAND_CPU = "sensors k10temp-pci-00c3";
		public static final String COMMAND_NVME0 = "sensors nvme-pci-2100"; // 1TO
		public static final String COMMAND_NVME1 = "sensors nvme-pci-2b00"; // 500go
	}

	public class Z390 {
		public static final String COMMAND_GPU = "sensors amdgpu-pci-0300";
		public static final String COMMAND_CPU = "sensors coretemp-isa-0000"; 
		public static final String COMMAND_NVME0 = "sensors nvme-pci-0600"; // 1 to
		public static final String COMMAND_NVME1 = "sensors nvme-pci-0400"; // 500 go
	}

	public static final int CPU_CAPTOR = 111;
	public static final int GPU_CAPTOR = 110;
	public static final int NVME_CAPTOR = 112;
	public static final Pattern CPU_TCTL = R7_5800X3D.CPU_TCTL;
	public static final Pattern GPU_JUNCTION = Pattern.compile("junction:[^+]*\\+(\\d+\\.\\d+)°C");
	public static final Pattern COMPOSITE = Pattern.compile("Composite:[^+]*\\+(\\d+\\.\\d+)°C");
	public static final String COMMAND_GPU = MSI_B550_5800X3D.COMMAND_GPU;
	public static final String COMMAND_CPU = MSI_B550_5800X3D.COMMAND_CPU;
	public static final String COMMAND_NVME = MSI_B550_5800X3D.COMMAND_NVME0;
	public static final String HOST = "http://homeiot/captor/";

	
	
}
