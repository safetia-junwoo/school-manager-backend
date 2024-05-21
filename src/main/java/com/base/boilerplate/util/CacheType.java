package com.base.boilerplate.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum CacheType {
    USERS(NAMES.USER),
    MENU(NAMES.MENU),
    MENU_DETAIL(NAMES.MENU_DETAIL),
    ROLE(NAMES.ROLES),
    CRUD_ROLE(NAMES.CRUD_ROLE),
    PLANT(NAMES.PLANT),
    FACTORY(NAMES.FACTORY),
    DEPARTMENT(NAMES.DEPARTMENT),
    NOTICEBOARD(NAMES.NOTICEBOARD),
    NOTICEBOARD_DETAIL(NAMES.NOTICEBOARD_DETAIL),
    HAZARDFACTOR(NAMES.HAZARDFACTOR),
    CHECKUPRESULTCODE(NAMES.CHECKUPRESULTCODE),
    MEDICALCHECKUPSET(NAMES.MEDICALCHECKUPSET),
    MEDICATIONSUPPLIES(NAMES.MEDICATIONSUPPLIES),
    MEDICATIONSUPPLIES_DETAIL(NAMES.MEDICATIONSUPPLIES_DETAIL),
    MEDICATIONSUPPLIES_PROPER_STOCK(NAMES.MEDICATIONSUPPLIES_PROPER_STOCK),
    MEDICATIONSUPPLIES_INVENTORY(NAMES.MEDICATIONSUPPLIES_INVENTORY),
    MEDICATIONSUPPLIES_INVENTORY_DETAIL(NAMES.MEDICATIONSUPPLIES_INVENTORY_DETAIL),
    MEDICATIONSUPPLIES_ORDER_MANAGEMENT(NAMES.MEDICATIONSUPPLIES_ORDER_MANAGEMENT),
    MEDICATIONSUPPLIES_ORDER_MANAGEMENT_DETAIL(NAMES.MEDICATIONSUPPLIES_ORDER_MANAGEMENT_DETAIL),
    OFFICESUPPLIES(NAMES.OFFICESUPPLIES),
    VACCINATIONMANAGEMENT(NAMES.VACCINATIONMANAGEMENT),
    VACCINE(NAMES.VACCINE),
    FILE(NAMES.FILE),
    THUMBNAIL_FILE(NAMES.THUMBNAIL_FILE),
    USER_HOSPITAL(NAMES.USER_HOSPITAL),
    CHARGER_HOSPITAL(NAMES.CHARGER_HOSPITAL),
    CHARGER_HOSPITAL_DETAIL(NAMES.CHARGER_HOSPITAL_DETAIL),
    SPOUSE(NAMES.SPOUSE),
    MEDICAL_CHECKUP_ADDITIONAL_ITEM_CATEGORY(NAMES.MEDICAL_CHECKUP_ADDITIONAL_ITEM_CATEGORY),
    MEDICAL_CHECKUP_ADDITIONAL_ITEM(NAMES.MEDICAL_CHECKUP_ADDITIONAL_ITEM),
    // 구분관리
    MEDICAL_CHECKUP_SORT_MANAGEMENT(NAMES.MEDICAL_CHECKUP_SORT_MANAGEMENT),
    MEDICAL_CHECKUP_SORT_MANAGEMENT_DETAIL(NAMES.MEDICAL_CHECKUP_SORT_MANAGEMENT_DETAIL),
    // 의약품 키트 관리
    MEDICATION_KIT(NAMES.MEDICATION_KIT),
    // 의약품 키트 그룹
    MEDICATION_KIT_GROUP(NAMES.MEDICATION_KIT_GROUP),
    // EMAIL,SMS 템플릿 관리
    SEND_TEMPLATE(NAMES.SEND_TEMPLATE),
    // 설문관리 - 마스터
    SURVEY_MASTER(NAMES.SURVEY_MASTER),
    // 설문관리 - 질문
    SURVEY_QUESTION(NAMES.SURVEY_QUESTION),
    // 설문관리 - 답변
    SURVEY_ANSWER(NAMES.SURVEY_ANSWER),
    // 설문관리 - 아이템
    SURVEY_ITEM(NAMES.SURVEY_ITEM),
    // 설문관리 - 결과
    SURVEY_RESULT(NAMES.SURVEY_RESULT),
    MEDICAL_CHECKUP_TARGET(NAMES.MEDICAL_CHECKUP_TARGET),
    MEDICAL_CHECKUP_RESERVATION(NAMES.MEDICAL_CHECKUP_RESERVATION),
    MEDICAL_CHECKUP_RESULT(NAMES.MEDICAL_CHECKUP_RESULT),

    // 동의서
    AGREEMENT_MASTER(NAMES.AGREEMENT_MASTER),
    // 근무지역
    WORK_AREA(NAMES.WORK_AREA),
    // 사원 번호 관리
    EMPLOYEE_NUMBER_HISTORY(NAMES.EMPLOYEE_NUMBER_HISTORY),
    // 임직원 관리
    USER_MANAGEMENT(NAMES.USER_MANAGEMENT),
    // 검진 지원 관리
    MEDICAL_CHECKUP_SUPPORT_MANAGEMENT(NAMES.MEDICAL_CHECKUP_SUPPORT_MANAGEMENT),
    ;

    private String name;
    private int expireAfterWrite;
    private int maximumSize;

    CacheType(String name) {
        this.name = name;
        this.expireAfterWrite = ConstConfig.DEFAULT_TTL_SEC;
        this.maximumSize = ConstConfig.DEFAULT_MAX_SIZE;
    }

    static class ConstConfig {
        static final int DEFAULT_TTL_SEC = 3600;
        static final int DEFAULT_MAX_SIZE = 10000;
    }
    public static class NAMES {
        public static final String USER = "user";
        public static final String MENU = "menu";
        public static final String ROLES = "roles";
        public static final String CRUD_ROLE = "crudRole";
        public static final String MENU_DETAIL = "menuDetail";
        public static final String PLANT = "plant";
        public static final String FACTORY = "factory";
        public static final String DEPARTMENT = "department";
        public static final String NOTICEBOARD = "noticeBoard";
        public static final String NOTICEBOARD_DETAIL = "noticeBoardDetail";
        public static final String HAZARDFACTOR = "hazardFactor";
        public static final String CHECKUPRESULTCODE = "checkupResultCode";
        public static final String MEDICALCHECKUPSET = "medicalCheckupSet";
        public static final String MEDICATIONSUPPLIES = "medicationSupplies";
        public static final String MEDICATIONSUPPLIES_DETAIL = "medicationSuppliesDetail";
        public static final String MEDICATIONSUPPLIES_PROPER_STOCK = "medicationSuppliesProperStock";
        public static final String MEDICATIONSUPPLIES_INVENTORY = "medicationSuppliesInventory";
        public static final String MEDICATIONSUPPLIES_INVENTORY_DETAIL = "medicationSuppliesInventoryDetail";
        public static final String MEDICATIONSUPPLIES_ORDER_MANAGEMENT = "medicationOrderManagement";
        public static final String MEDICATIONSUPPLIES_ORDER_MANAGEMENT_DETAIL = "medicationOrderManagementDetail";
        public static final String OFFICESUPPLIES = "officeSupplies";
        public static final String VACCINATIONMANAGEMENT = "vaccinationManagement";
        public static final String VACCINE = "vaccine";
        public static final String FILE = "file";
        public static final String THUMBNAIL_FILE = "thumbnailFile";
        public static final String USER_HOSPITAL = "userHospital";
        public static final String CHARGER_HOSPITAL = "chargerHospital";
        public static final String CHARGER_HOSPITAL_DETAIL = "chargerHospitalDetail";
        public static final String SPOUSE = "spouse";

        public static final String MEDICAL_CHECKUP_ADDITIONAL_ITEM = "medicalCheckupAdditionalItem";
        public static final String MEDICAL_CHECKUP_ADDITIONAL_ITEM_CATEGORY = "medicalCheckupAdditionalItemCategory";
        // 구분 관리
        public static final String MEDICAL_CHECKUP_SORT_MANAGEMENT = "medicalCheckupSortManagement";
        public static final String MEDICAL_CHECKUP_SORT_MANAGEMENT_DETAIL = "medicalCheckupSortManagementDetail";
        // 의약품 키트
        public static final String MEDICATION_KIT = "medicationKit";
        // 의약품 키트 그룹
        public static final String MEDICATION_KIT_GROUP = "medicationKitGroup";
        // Mail SMS 관리
        public static final String SEND_TEMPLATE = "sendTemplate";
        // 설문관리 - 마스터
        public static final String SURVEY_MASTER = "surveyMaster";
        // 설문관리 - 질문
        public static final String SURVEY_QUESTION = "surveyQuestion";
        // 설문관리 - 답변
        public static final String SURVEY_ANSWER = "surveyAnswer";
        // 설문관리 - 아이템
        public static final String SURVEY_ITEM = "surveyItem";
        // 설문관리 - 결과
        public static final String SURVEY_RESULT = "surveyResult";
        // 건강검진 - 대상자
        public static final String MEDICAL_CHECKUP_TARGET = "medicalCheckupTarget";
        // 건강검진 - 예약
        public static final String MEDICAL_CHECKUP_RESERVATION = "medicalCheckupReservation";
        // 건강검진 - 결과
        public static final String MEDICAL_CHECKUP_RESULT = "medicalCheckupResult";
        // 동의서
        public static final String AGREEMENT_MASTER = "agreementMaster";
        // 근무 지역
        public static final String WORK_AREA = "workArea";
        // 사원 번호
        public static final String EMPLOYEE_NUMBER_HISTORY = "employeeNumberHistory";
        public static final String USER_MANAGEMENT = "userManagement";
        // 검진 지원 관리
        public static final String MEDICAL_CHECKUP_SUPPORT_MANAGEMENT = "medicalCheckupSupportManagement";
    }
}
