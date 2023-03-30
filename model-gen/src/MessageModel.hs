{-# LANGUAGE QuasiQuotes #-}
{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE BangPatterns #-}
{-# LANGUAGE BinaryLiterals #-}
{-# LANGUAGE DeriveGeneric #-}
module MessageModel where

import qualified Metadata               as Meta
import qualified Data.List              as L
import qualified Data.Text              as T

-- Enums
enumRightMode = Meta.EnumerateField
    { Meta.fieldName    = "RightLevel"
    , Meta.fieldType    = "EnumRightMode"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "INVALID"
                        , Meta.enumValue = 0
                        , Meta.enumComment = "无权限"
                        }
        , Meta.EnumItem { Meta.enumName = "LEVEL1"
                        , Meta.enumValue = 1
                        , Meta.enumComment = "具备数据读的权限,当用户可以读某个数据，而无法写任何数据时返回这一权限值。"
                        }
        , Meta.EnumItem { Meta.enumName = "LEVEL2"
                        , Meta.enumValue = 2
                        , Meta.enumComment = "具备数据读、写的权限，当用户对某个数据具有读写权限时返回这一权限值。"
                        }
        ]
    , Meta.fieldComment = "监控系统下级SC向上级SC提供的权限定义"
    }

enumResult = Meta.EnumerateField
    { Meta.fieldName    = "Result"
    , Meta.fieldType    = "EnumResult"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "FAILURE"
                        , Meta.enumValue = 0
                        , Meta.enumComment = "失败"
                        }
        , Meta.EnumItem { Meta.enumName = "SUCCESS"
                        , Meta.enumValue = 1
                        , Meta.enumComment = "成功"
                        }
        ]
    , Meta.fieldComment = "报文返回结果"
    }

enumType = Meta.EnumerateField
    { Meta.fieldName    = "Type"
    , Meta.fieldType    = "EnumType"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "STATION"
                        , Meta.enumValue = 0
                        , Meta.enumComment = "局、站"
                        }
        , Meta.EnumItem { Meta.enumName = "DEVICE"
                        , Meta.enumValue = 1
                        , Meta.enumComment = "设备"
                        }
        , Meta.EnumItem { Meta.enumName = "DI"
                        , Meta.enumValue = 2
                        , Meta.enumComment = "数字输入量（包含多态数字输入量），遥信"
                        }
        , Meta.EnumItem { Meta.enumName = "AI"
                        , Meta.enumValue = 3
                        , Meta.enumComment = "模拟输入量，遥测"
                        }
        , Meta.EnumItem { Meta.enumName = "DO"
                        , Meta.enumValue = 4
                        , Meta.enumComment = "数字输出量，遥控"
                        }
        , Meta.EnumItem { Meta.enumName = "AO"
                        , Meta.enumValue = 5
                        , Meta.enumComment = "模拟输出量，遥调"
                        }
        ]
    , Meta.fieldComment = "监控系统数据的种类"
    }

enumAlarmLevel = Meta.EnumerateField
    { Meta.fieldName    = "AlarmLevel"
    , Meta.fieldType    = "EnumAlarmLevel"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "NOALARM"
                        , Meta.enumValue = 0
                        , Meta.enumComment = "无告警"
                        }
        , Meta.EnumItem { Meta.enumName = "CRITICAL"
                        , Meta.enumValue = 1
                        , Meta.enumComment = "一级告警"
                        }
        , Meta.EnumItem { Meta.enumName = "MAJOR"
                        , Meta.enumValue = 2
                        , Meta.enumComment = "二级告警"
                        }
        , Meta.EnumItem { Meta.enumName = "MINOR"
                        , Meta.enumValue = 3
                        , Meta.enumComment = "三级告警"
                        }
        , Meta.EnumItem { Meta.enumName = "HINT"
                        , Meta.enumValue = 4
                        , Meta.enumComment = "四级告警"
                        }
        ]
    , Meta.fieldComment = "告警的等级"
    }

enumEnable = Meta.EnumerateField
    { Meta.fieldName    = "Enable"
    , Meta.fieldType    = "EnumEnable"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "DISABLE"
                        , Meta.enumValue = 0
                        , Meta.enumComment = "禁止/不能"
                        }
        , Meta.EnumItem { Meta.enumName = "ENABLE"
                        , Meta.enumValue = 1
                        , Meta.enumComment = "开放/能"
                        }
        ]
    , Meta.fieldComment = "使能的属性"
    }

enumShield = Meta.EnumerateField
    { Meta.fieldName    = "Shield"
    , Meta.fieldType    = "EnumShield"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "YES"
                        , Meta.enumValue = 0
                        , Meta.enumComment = "屏蔽"
                        }
        , Meta.EnumItem { Meta.enumName = "ENABLE"
                        , Meta.enumValue = 1
                        , Meta.enumComment = "不屏蔽"
                        }
        ]
    , Meta.fieldComment = "是否屏蔽"
    }

enumAccessMode = Meta.EnumerateField
    { Meta.fieldName    = "AccessMode"
    , Meta.fieldType    = "EnumAccessMode"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "ASK_ANSWER"
                        , Meta.enumValue = 0
                        , Meta.enumComment = "一问一答方式"
                        }
        , Meta.EnumItem { Meta.enumName = "CHANGE_TRIGGER"
                        , Meta.enumValue = 1
                        , Meta.enumComment = "改变时自动发送数据方式"
                        }
        , Meta.EnumItem { Meta.enumName = "TIME_TRIGGER"
                        , Meta.enumValue = 2
                        , Meta.enumComment = "定时发送数据方式"
                        }
        , Meta.EnumItem { Meta.enumName = "STOP"
                        , Meta.enumValue = 3
                        , Meta.enumComment = "停止发送数据方式"
                        }
        ]
    , Meta.fieldComment = "下级实时数据访问的方式"
    }

enumState = Meta.EnumerateField
    { Meta.fieldName    = "State"
    , Meta.fieldType    = "EnumState"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "NOALARM"
                        , Meta.enumValue = 0
                        , Meta.enumComment = "无告警"
                        }
        , Meta.EnumItem { Meta.enumName = "CRITICAL"
                        , Meta.enumValue = 1
                        , Meta.enumComment = "一级告警"
                        }
        , Meta.EnumItem { Meta.enumName = "MAJOR"
                        , Meta.enumValue = 2
                        , Meta.enumComment = "二级告警"
                        }
        , Meta.EnumItem { Meta.enumName = "MINOR"
                        , Meta.enumValue = 3
                        , Meta.enumComment = "三级告警"
                        }
        , Meta.EnumItem { Meta.enumName = "HINT"
                        , Meta.enumValue = 4
                        , Meta.enumComment = "四级告警"
                        }
        , Meta.EnumItem { Meta.enumName = "OPEVENT"
                        , Meta.enumValue = 5
                        , Meta.enumComment = "操作事件"
                        }
        , Meta.EnumItem { Meta.enumName = "INVALID"
                        , Meta.enumValue = 6
                        , Meta.enumComment = "无效数据"
                        }
        ]
    , Meta.fieldComment = "数值的状态"
    }

enumAlarmMode = Meta.EnumerateField
    { Meta.fieldName    = "AlarmMode"
    , Meta.fieldType    = "EnumAlarmMode"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "NOALARM"
                        , Meta.enumValue = 0
                        , Meta.enumComment = "不做告警上报"
                        }
        , Meta.EnumItem { Meta.enumName = "CRITICAL"
                        , Meta.enumValue = 1
                        , Meta.enumComment = "一级告警上报"
                        }
        , Meta.EnumItem { Meta.enumName = "MAJOR"
                        , Meta.enumValue = 2
                        , Meta.enumComment = "二级告警上报"
                        }
        , Meta.EnumItem { Meta.enumName = "MINOR"
                        , Meta.enumValue = 3
                        , Meta.enumComment = "三级告警上报"
                        }
        , Meta.EnumItem { Meta.enumName = "HINT"
                        , Meta.enumValue = 4
                        , Meta.enumComment = "四级告警上报"
                        }
        ]
    , Meta.fieldComment = "告警等级设定的模式"
    }

enumModifyType = Meta.EnumerateField
    { Meta.fieldName    = "ModifyType"
    , Meta.fieldType    = "EnumModifyType"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "ADDNONODES"
                        , Meta.enumValue = 0
                        , Meta.enumComment = "新增（无子节点）"
                        }
        , Meta.EnumItem { Meta.enumName = "ADDINNODES"
                        , Meta.enumValue = 1
                        , Meta.enumComment = "新增（含子节点）"
                        }
        , Meta.EnumItem { Meta.enumName = "DELETE"
                        , Meta.enumValue = 2
                        , Meta.enumComment = "删除"
                        }
        , Meta.EnumItem { Meta.enumName = "MODIFYNONODES"
                        , Meta.enumValue = 3
                        , Meta.enumComment = "修改（仅修改本节点）"
                        }
        , Meta.EnumItem { Meta.enumName = "MODIFYINNODES"
                        , Meta.enumValue = 4
                        , Meta.enumComment = "修改（涉及到子节点）"
                        }
        ]
    , Meta.fieldComment = "对象属性修改类型"
    }

enumPKType = Meta.EnumerateField
    { Meta.fieldName    = "PKType"
    , Meta.fieldType    = "EnumPKType"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldEnums   =
        [ Meta.EnumItem { Meta.enumName = "LOGIN"
                        , Meta.enumValue = 101
                        , Meta.enumComment = "登录"
                        }
        , Meta.EnumItem { Meta.enumName = "LOGIN_ACK"
                        , Meta.enumValue = 102
                        , Meta.enumComment = "登录响应"
                        }
        , Meta.EnumItem { Meta.enumName = "LOGOUT"
                        , Meta.enumValue = 103
                        , Meta.enumComment = "登出"
                        }
        , Meta.EnumItem { Meta.enumName = "LOGOUT_ACK"
                        , Meta.enumValue = 104
                        , Meta.enumComment = "登出响应"
                        }
        , Meta.EnumItem { Meta.enumName = "SET_DYN_ACCESS_MODE"
                        , Meta.enumValue = 401
                        , Meta.enumComment = "请求实时数据方式设置"
                        }
        , Meta.EnumItem { Meta.enumName = "DYN_ACCESS_MODE_ACK"
                        , Meta.enumValue = 402
                        , Meta.enumComment = "实时数据响应"
                        }
        , Meta.EnumItem { Meta.enumName = "SET_ALARM_MODE"
                        , Meta.enumValue = 501
                        , Meta.enumComment = "请求告警数据方式设置"
                        }
        , Meta.EnumItem { Meta.enumName = "ALARM_MODE_ACK"
                        , Meta.enumValue = 502
                        , Meta.enumComment = "告警方式设置响应"
                        }
        , Meta.EnumItem { Meta.enumName = "SEND_ALARM"
                        , Meta.enumValue = 503
                        , Meta.enumComment = "实时告警发送"
                        }
        , Meta.EnumItem { Meta.enumName = "SEND_ALARM_ACK"
                        , Meta.enumValue = 504
                        , Meta.enumComment = "实时告警发送确认"
                        }
        , Meta.EnumItem { Meta.enumName = "SET_POINT"
                        , Meta.enumValue = 1001
                        , Meta.enumComment = "写数据请求"
                        }
        , Meta.EnumItem { Meta.enumName = "SET_POINT_ACK"
                        , Meta.enumValue = 1002
                        , Meta.enumComment = "写数据响应"
                        }
        , Meta.EnumItem { Meta.enumName = "REQ_MODIFY_PASSWORD"
                        , Meta.enumValue = 1101
                        , Meta.enumComment = "改口令请求"
                        }
        , Meta.EnumItem { Meta.enumName = "MODIFY_PASSWORD_ACK"
                        , Meta.enumValue = 1102
                        , Meta.enumComment = "改口令响应"
                        }
        , Meta.EnumItem { Meta.enumName = "HEART_BEAT"
                        , Meta.enumValue = 1201
                        , Meta.enumComment = "确认连接"
                        }
        , Meta.EnumItem { Meta.enumName = "HEART_BEAT_ACK"
                        , Meta.enumValue = 1202
                        , Meta.enumComment = "回应连接"
                        }
        , Meta.EnumItem { Meta.enumName = "TIME_CHECK"
                        , Meta.enumValue = 1301
                        , Meta.enumComment = "发送时钟消息"
                        }
        , Meta.EnumItem { Meta.enumName = "TIME_CHECK_ACK"
                        , Meta.enumValue = 1302
                        , Meta.enumComment = "时钟同步响应"
                        }
        , Meta.EnumItem { Meta.enumName = "NOTIFY_PROPERTY_MODIFY"
                        , Meta.enumValue = 1501
                        , Meta.enumComment = "通知数据属性修改"
                        }
        , Meta.EnumItem { Meta.enumName = "PROPERTY_MODIFY_ACK"
                        , Meta.enumValue = 1502
                        , Meta.enumComment = "通知数据属性修改响应"
                        }
        ]
    , Meta.fieldComment = "报文定义"
    }


-- Data Structure Fields

-- TTime
years = Meta.Int16Field
    { Meta.fieldName    = "Years"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "年"
    }

month = Meta.Int8Field
    { Meta.fieldName    = "Month"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "月"
    }

day = Meta.Int8Field
    { Meta.fieldName    = "Day"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "日"
    }

hour = Meta.Int8Field
    { Meta.fieldName    = "Hour"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "时"
    }

minute = Meta.Int8Field
    { Meta.fieldName    = "Minute"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "分"
    }

second = Meta.Int8Field
    { Meta.fieldName    = "Second"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "秒"
    }

tTimeStruct = Meta.Struct
    { Meta.entityId      = ""
    , Meta.entityName    = "TTime"
    , Meta.entityComment = "时间的结构"
    , Meta.entityFields  =
        [ years
        , month
        , day
        , hour
        , minute
        , second
        ]
    }

tTime = Meta.EntityField
    { Meta.fieldName    = "Time"
    , Meta.fieldType    = "TTime"
    , Meta.fieldSize    = ""
    , Meta.fieldComment = "本机时间"
    }

-- TA/TD/TID
aiValue = Meta.Float32Field
    { Meta.fieldName    = "Value"
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "AI值"
    }

lscID = Meta.Int32Field
    { Meta.fieldName    = "LSCID"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "LSC ID"
    }

taStruct = Meta.Struct
    { Meta.entityId      = ""
    , Meta.entityName    = "TA"
    , Meta.entityComment = "模拟量的值的结构"
    , Meta.entityFields  =
        [ enumType
        , nodeId
        , lscID
        , aiValue
        , enumState
        ]
    }

diValue = Meta.Int8Field
    { Meta.fieldName    = "Value"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "DI值"
    }

tdStruct = Meta.Struct
    { Meta.entityId      = ""
    , Meta.entityName    = "TD"
    , Meta.entityComment = "数字量的值的结构"
    , Meta.entityFields  =
        [ enumType
        , nodeId
        , lscID
        , diValue
        , enumState
        ]
    }

-- TAlarm
alarmDesc = Meta.StringField
    { Meta.fieldName    = "AlarmDesc"
    , Meta.fieldSize    = "Lengths::ALARM_LENGTH"
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "告警描述"
    }

tAlarmStruct = Meta.Struct
    { Meta.entityId      = ""
    , Meta.entityName    = "TAlarm"
    , Meta.entityComment = "当前告警值的结构"
    , Meta.entityFields  =
        [ nodeId
        , lscID
        , enumState
        , alarmDesc
        ]
    }


-- Info Fields
userName = Meta.StringField
    { Meta.fieldName    = "UserName"
    , Meta.fieldSize    = "Lengths::USER_LENGTH"
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "Login user name."
    }

passWord = Meta.StringField
    { Meta.fieldName    = "PassWord"
    , Meta.fieldSize    = "Lengths::PASSWORD_LENGTH"
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "Login password."
    }

terminalID = Meta.Int32Field
    { Meta.fieldName    = "TerminalID"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "上级SCID"
    }

groupID = Meta.Int32Field
    { Meta.fieldName    = "GroupID"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "相应模式数据包的序号"
    }

pollingTime = Meta.Int32Field
    { Meta.fieldName    = "PollingTime"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "定时方式时的发送间隔秒数，小于1无效，若出现小于1的值，则按等于1处理。"
    }

nodeId = Meta.Int32Field
    { Meta.fieldName    = "Id"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = ""
    , Meta.fieldComment = "数据标识ID"
    }

nodeIds = Meta.EntityField
    { Meta.fieldName    = "Ids"
    , Meta.fieldType    = "NodeIDArray"
    , Meta.fieldSize    = ""
    , Meta.fieldComment = "如果类型是站点，即获取站内所有设备下的监控点数据；如果是设备，即获取该设备下所有监控点数据；如果是监控点，即是该点数据。"
    }

value = Meta.EntityField
    { Meta.fieldName    = "Value"
    , Meta.fieldType    = "TATD"
    , Meta.fieldSize    = ""
    , Meta.fieldComment = "5.1.8中的TA/TD的数据结构定义"
    }

values = Meta.EntityField
    { Meta.fieldName    = "Values"
    , Meta.fieldType    = "TATDArray"
    , Meta.fieldSize    = ""
    , Meta.fieldComment = "返回正确数据值得数量及值对应对应的值5.1.8中的TA/TD的数据结构定义"
    }

alarms = Meta.EntityField
    { Meta.fieldName    = "Values"
    , Meta.fieldType    = "TAlarmArray"
    , Meta.fieldSize    = ""
    , Meta.fieldComment = "告警信息数量及清单。见TAlarm的数据结构定义"
    }


-- Message header and tail CRC16 fields

msgHeader = Meta.Int32Field
    { Meta.fieldName    = "Header"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = "0x7E7C6B5A"
    , Meta.fieldComment = "Message header, 0x7E7C6B5A."
    }

msgLength = Meta.Int32Field
    { Meta.fieldName    = "Length"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = "0"
    , Meta.fieldComment = "Message length."
    }

msgSerialNo = Meta.Int32Field
    { Meta.fieldName    = "SerialNo"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = "0"
    , Meta.fieldComment = "Message serial number."
    }

msgPKType = Meta.Int32Field
    { Meta.fieldName    = "PKType"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = "EnumPKType::HEAT_BEAT"
    , Meta.fieldComment = "PKType."
    }

msgCRC16 = Meta.UInt16Field
    { Meta.fieldName    = "CRC16"
    , Meta.fieldSize    = ""
    , Meta.fieldValue   = "0"
    , Meta.fieldComment = "Message CRC16."
    }

-- messages
login = Meta.Message
    { Meta.entityId      = "EnumPKType::LOGIN"
    , Meta.entityName   = "Login"
    , Meta.entityComment = "登录"
    , Meta.entityFields =
        [ userName
        , passWord
        ]
    }

loginAck = Meta.Message
    { Meta.entityId      = "EnumPKType::LOGIN_ACK"
    , Meta.entityName   = "LoginAck"
    , Meta.entityComment = "登录响应"
    , Meta.entityFields =
        [ enumRightMode
        ]
    }

logout = Meta.Message
    { Meta.entityId      = "EnumPKType::LOGOUT"
    , Meta.entityName   = "Logout"
    , Meta.entityComment = "登出"
    , Meta.entityFields = []
    }

logoutAck = Meta.Message
    { Meta.entityId      = "EnumPKType::LOGOUT_ACK"
    , Meta.entityName   = "LogoutAck"
    , Meta.entityComment = "登出响应"
    , Meta.entityFields =
        [ enumResult
        ]
    }

setDynAccessMode = Meta.Message
    { Meta.entityId      = "EnumPKType::SET_DYN_ACCESS_MODE"
    , Meta.entityName   = "SetDynAccessMode"
    , Meta.entityComment = "请求实时数据方式设置"
    , Meta.entityFields =
        [ terminalID
        , groupID
        , enumAccessMode { Meta.fieldName = "Mode" }
        , pollingTime
        , nodeIds 
        ]
    }

dynAccessModeAck = Meta.Message
    { Meta.entityId      = "EnumPKType::DYN_ACCESS_MODE_ACK"
    , Meta.entityName   = "DynAccessModeAck"
    , Meta.entityComment = "实时数据响应"
    , Meta.entityFields =
        [ terminalID
        , groupID
        , enumResult
        , values { Meta.fieldName = "Values" }
        ]
    }

setAlarmMode = Meta.Message
    { Meta.entityId      = "EnumPKType::SET_ALARM_MODE"
    , Meta.entityName   = "SetAlarmMode"
    , Meta.entityComment = "请求告警数据方式设置"
    , Meta.entityFields =
        [ groupID
        , enumAlarmMode { Meta.fieldName = "Mode" }
        , nodeIds
        ]
    }

alarmModeAck = Meta.Message
    { Meta.entityId      = "EnumPKType::ALARM_MODE_ACK"
    , Meta.entityName   = "AlarmModeAck"
    , Meta.entityComment = "告警方式设置响应"
    , Meta.entityFields =
        [ groupID
        , enumResult
        ]
    }

sendAlarm = Meta.Message
    { Meta.entityId      = "EnumPKType::SEND_ALARM"
    , Meta.entityName   = "SendAlarm"
    , Meta.entityComment = "实时告警发送"
    , Meta.entityFields =
        [ alarms
        ]
    }

sendAlarmAck = Meta.Message
    { Meta.entityId      = "EnumPKType::SEND_ALARM_ACK"
    , Meta.entityName   = "SendAlarmAck"
    , Meta.entityComment = "实时告警发送确认"
    , Meta.entityFields =
        [ alarms
        ]
    }

setPoint = Meta.Message
    { Meta.entityId      = "EnumPKType::SET_POINT"
    , Meta.entityName   = "SetPoint"
    , Meta.entityComment = "写数据请求"
    , Meta.entityFields =
        [ value
        ]
    }

setPointAck = Meta.Message
    { Meta.entityId      = "EnumPKType::SET_POINT_ACK"
    , Meta.entityName   = "SetPointAck"
    , Meta.entityComment = "写数据响应"
    , Meta.entityFields =
        [ lscID
        , nodeId
        , enumResult
        ]
    }

modifyPassword = Meta.Message
    { Meta.entityId      = "EnumPKType::REQ_MODIFY_PASSWORD"
    , Meta.entityName   = "ModifyPassword"
    , Meta.entityComment = "改口令请求"
    , Meta.entityFields =
        [ userName
        , passWord { Meta.fieldName = "OldPassWord" }
        , passWord { Meta.fieldName = "NewPassWord" }
        ]
    }

modifyPasswordAck = Meta.Message
    { Meta.entityId      = "EnumPKType::MODIFY_PASSWORD_ACK"
    , Meta.entityName   = "ModifyPasswordAck"
    , Meta.entityComment = "改口令响应"
    , Meta.entityFields =
        [ enumResult
        ]
    }

heartBeat = Meta.Message
    { Meta.entityId      = "EnumPKType::HEART_BEAT"
    , Meta.entityName   = "HeartBeat"
    , Meta.entityComment = "确认连接"
    , Meta.entityFields = []
    }

heartBeatAck = Meta.Message
    { Meta.entityId      = "EnumPKType::HEART_BEAT_ACK"
    , Meta.entityName   = "HeartBeatAck"
    , Meta.entityComment = "回应连接"
    , Meta.entityFields = []
    }

timeCheck = Meta.Message
    { Meta.entityId      = "EnumPKType::TIME_CHECK"
    , Meta.entityName   = "TimeCheck"
    , Meta.entityComment = "发送时钟消息"
    , Meta.entityFields =
        [ tTime
        ]
    }

timeCheckAck = Meta.Message
    { Meta.entityId      = "EnumPKType::TIME_CHECK_ACK"
    , Meta.entityName   = "TimeCheckAck"
    , Meta.entityComment = "时钟同步响应"
    , Meta.entityFields =
        [ enumResult
        ]
    }

notifyPropertyModify = Meta.Message
    { Meta.entityId      = "EnumPKType::NOTIFY_PROPERTY_MODIFY"
    , Meta.entityName   = "NotifyPropertyModify"
    , Meta.entityComment = "修改数据设置通知"
    , Meta.entityFields =
        [ nodeId
        , enumModifyType
        ]
    }

propertyModifyAck = Meta.Message
    { Meta.entityId      = "EnumPKType::PROPERTY_MODIFY_ACK"
    , Meta.entityName   = "PropertyModifyAck"
    , Meta.entityComment = "修改通知收到"
    , Meta.entityFields = []
    }


model = Meta.Model
    { Meta.namespace = "cmcc::cint3"
    , Meta.headerFields = 
        [ msgHeader
        , msgLength
        , msgSerialNo
        , enumPKType
        ]
    , Meta.tailFields = 
        [ msgCRC16
        ]
    , Meta.entities =
        [ tTimeStruct
        , taStruct
        , tdStruct
        , tAlarmStruct
        , login
        , loginAck
        , logout
        , logoutAck
        , setDynAccessMode
        , dynAccessModeAck
        , setAlarmMode
        , alarmModeAck
        , sendAlarm
        , sendAlarmAck
        , setPoint
        , setPointAck
        , modifyPassword
        , modifyPasswordAck
        , heartBeat
        , heartBeatAck
        , timeCheck
        , timeCheckAck
        , notifyPropertyModify
        , propertyModifyAck
        ]
    , Meta.enumerates =
        [ enumRightMode
        , enumResult
        , enumType
        , enumAlarmLevel
        , enumEnable
        , enumShield
        , enumAccessMode
        , enumState
        , enumAlarmMode
        , enumModifyType
        , enumPKType
        ]
    }

