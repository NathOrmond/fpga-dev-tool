package com.nathanormond.obsrv;

public class ActionCommands {

	public static final String GUI_HOME = "gui_home";
	
	/**************************
	 * Menu Bar
	 */
	
	public static final String CONFIG_MENU_ITEM = "config_menu_item";
	public static final String LOG_OUTPUT_MENU_ITEM = "Log Output";
	
	/**************************
	 * IP Address Configuration
	 */
	
	public static final String SET_IP_ADDR = "set_ip_address";
	public static final String CHOOSE_FILE = "choose_file";
	
	/**************************
	 * Main Menu
	 */
	
	public static final String SELECT_COMMAND_MODE = "select loopback";
	public static final String SELECT_TAP_ON_MODE = "select tap on mode";
	public static final String SELECT_FPGA_PROGRAMMER_MODE = "select fpga programmer";
	
	/**************************
	 * Loop Back Test Menu
	 */
	
	public static final String N_NUMBER_COMMANDS = "n_number_commands";
	public static final String INFINITE_NUMBER_COMMANDS = "infinite_commands";
	public static final String SET_LOOP_DELAY_TIME = "set_loop_delay_time"; 
	public static final String SET_NUM_COMMANDS = "set_num_commands";
	public static final String LOG_FILE_CHECKBOX = "log_file_checkbox";
	public static final String START = "start";
	public static final String STOP = "stop";
	
	public static final String fullPacketOutput = "full";
	public static final String loopBackOnlyOutput = "\"bespoke\" :)";
	
	/**************************
	 * Tap On Test Menu
	 */
	
	/**************************
	 * FPGA Programmer Menu
	 */
	
	public static final String INTERROGATE_RUNNING_REGION = "running region interrogate";
	public static final String INTERROGATE_SELECTED_REGION = "selected region interrogate";
	public static final String INTERROGATE_ALL_REGIONS = "all regions interrogate";
	
	public static final String ERASE_REGION = "ERASE";
	public static final String SET_RUNNING = "CURRENT";
	public static final String UPLOAD_NEW_BUILD = "UPLOAD";
	
}
