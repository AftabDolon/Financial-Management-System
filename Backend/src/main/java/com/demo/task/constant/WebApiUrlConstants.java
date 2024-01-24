package com.demo.task.constant;

public class WebApiUrlConstants {
    public final static String API_URI_PREFIX = "/api/v1";
    public final static String PATH_VAR_ID = "/{id}";

    public final static String USER_API = "/user";
    public final static String USER_LOGIN_API = USER_API + "/login";
    public final static String USER_LOGOUT_API = USER_API + "/logout";
    public final static String USER_SAVE_DATA_API = USER_API + "/save-data";
    public final static String USER_UPDATE_DATA_API = USER_API + "/update-data";
    public final static String USER_ROLE_SAVE_API = USER_API + "/save-role";
    public final static String USER_ROLE_UPDATE_API = USER_API + "/update-role";
    public final static String USER_ROLE_INFO_API = USER_API + "/role/role-list";
    public final static String USER_ROLE_ASSIGN_API = USER_API + "/save/user-role";
    public final static String USER_ROLE_GET_API = USER_API + "/user-role-list/{userId}";
    public final static String USER_LIST_SINGLE_API = USER_API + "/user-lists";
    public final static String USER_PROFILE_API = USER_API + "/user-profile";
    public final static String USER_PROFILE_DISPLAY_API = USER_API + "/user-profile-display";
    public final static String USER_ACTIVITY_LOG_API = USER_API + "/user-activity-log";
    public final static String ALL_USER_ACTIVITY_LOG_API = USER_API + "/all-user-activity-log/{username}";
    public final static String ALL_USER_ACTIVITY_LIST_API = USER_API + "/user-activity-list";
    public final static String USER_PASSWORD_RESET_API = USER_API + "/user-reset-password/{username}";

    // Employee
    public final static String EMPLOYEE_API = "/employee";
    public final static String EMPLOYEE_SAVE_API = EMPLOYEE_API + "/save-data";
    public final static String EMPLOYEE_UPDATE_API = EMPLOYEE_API + "/update-data";

    // Dash Board
    public final static String DASHBOARD_API = "/dashboard";
    public final static String USER_INFO_API = DASHBOARD_API + "/user-info";
    public final static String COLLECTED_AMOUNTS = DASHBOARD_API + "/collected-amounts";

    //salary
    public final static String BALANCE_API = "/balance";
    public final static String BALANCE_SAVE_API = BALANCE_API + "/save-data";
    public final static String TRANSFER_SALARY_API = BALANCE_API + "/transfer-salary/{date}";

    // Menu
    public final static String MENU_API = USER_API + "/menu";
    public final static String MENU_SAVE_API = MENU_API + "/save";
    public final static String MENU_UPDATE_API = MENU_API + "/update";
    public final static String MENU_INFORMATION_API = MENU_API + "/menu-list";
    public final static String MENU_ID_NAME_API = MENU_API + "/menuListName";
    public final static String ROLE_MENU_SUBMENU_SAVE_API = MENU_API + "/role-menu-submenu/save";
    public final static String ROLE_MENU_SUBMENU_LIST_API = MENU_API + "/role-menu-submenu-list/{roleId}";
    public final static String ROLE_WISE_MENU_INFORMATION_API = MENU_API + "/role-menu-list";

    // SubMenu
    public final static String SUB_MENU_API = USER_API + "/submenu";
    public final static String SUB_MENU_SAVE_API = SUB_MENU_API + "/save";
    public final static String SUB_MENU_UPDATE_API = SUB_MENU_API + "/update";
    public final static String SUB_MENU_INFORMATION_API = SUB_MENU_API + "/submenu-list";


}
