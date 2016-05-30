package com.futronictech;

import android.annotation.SuppressLint;

public class AnsiSDKLib {
    /**
     * usb accessory模式调用该方法
     *
     * @param instance
     * @return
     */
    public native boolean OpenDevice(int instance);

    /**
     * usb host模式调用该方法
     *
     * @param io_ctx
     * @return
     */
    public native boolean OpenDeviceCtx(Object io_ctx);

    /**
     * 关闭模块
     */
    public native boolean CloseDevice();

    /**
     * 设置指纹图片的大小
     */
    public native boolean FillImageSize();

    /**
     * 采集指纹
     *
     * @param pImage 保存指纹图片数据的数组
     */
    public native boolean CaptureImage(byte[] pImage);

    /**
     * 创建模板
     *
     * @param finger        手指编号
     * @param pImage        保存指纹数据的数组
     * @param pTemplate     保存模板数据的数据（比实际值大）
     * @param pTemplateSize （模板的实际大小）
     */
    public native boolean CreateTemplate(int finger, byte[] pImage, byte[] pTemplate, int[] pTemplateSize);

    /**
     * 验证指纹
     *
     * @param finger        手指编号
     * @param pImage        要验证的模板的内容
     * @param pTemplate     接收图片数据的数组
     * @param pVerifyResult 验证结果
     */
    public native boolean VerifyTemplate(int finger, byte[] pImage, byte[] pTemplate, float[] pVerifyResult);

    /**
     * 匹配模板
     *
     * @param pProbeTemplate  要验证是否存在的模板
     * @param pGaleryTemplate 用来对比的模板
     * @param pMatchResult    匹配结果
     */
    public native boolean MatchTemplates(byte[] pProbeTemplate, byte[] pGaleryTemplate, float[] pMatchResult);

    /**
     * 将ansi的模板转换成iso的模板
     *
     * @param pAnsiTemplate
     * @param pIsoTemplate
     * @param pIsoTemplateSize
     * @return
     */
    public native boolean ConvertAnsiTemplateToIso(byte[] pAnsiTemplate, byte[] pIsoTemplate, int[] pIsoTemplateSize);

    /**
     * 获取模板数据的最大值
     */
    public native int GetMaxTemplateSize();

    public native boolean IsFingerPresent();


    // error code
    static public final int FTR_ANSISDK_ERROR_NO_ERROR = 0;
    static public final int FTR_ERROR_EMPTY_FRAME = 4306; /* ERROR_EMPTY */
    static public final int FTR_ERROR_MOVABLE_FINGER = 0x20000001;
    static public final int FTR_ERROR_NO_FRAME = 0x20000002;
    static public final int FTR_ERROR_HARDWARE_INCOMPATIBLE = 0x20000004;
    static public final int FTR_ERROR_FIRMWARE_INCOMPATIBLE = 0x20000005;
    static public final int FTR_ERROR_INVALID_AUTHORIZATION_CODE = 0x20000006;
    static public final int FTR_ERROR_WRITE_PROTECT = 19;
    static public final int FTR_ERROR_NOT_READY = 21;
    static public final int FTR_ERROR_NOT_ENOUGH_MEMORY = 8;
    static public final int FTR_ERROR_NO_MORE_ITEMS = 259; /*No item during device enum*/

    static public final int FTR_ERROR_NO_ERROR = 0;
    static public final int FTR_ANSISDK_ERROR_IMAGE_SIZE_NOT_SUP = 0x30000001;
    static public final int FTR_ANSISDK_ERROR_EXTRACTION_UNSPEC = 0x30000002;
    static public final int FTR_ANSISDK_ERROR_EXTRACTION_BAD_IMP = 0x30000003;
    static public final int FTR_ANSISDK_ERROR_MATCH_NULL = 0x30000004;
    static public final int FTR_ANSISDK_ERROR_MATCH_PARSE_PROBE = 0x30000005;
    static public final int FTR_ANSISDK_ERROR_MATCH_PARSE_GALLERY = 0x30000006;
    static public final int FTR_ANSISDK_ERROR_MORE_DATA = 0x30000007;

    /* Position type codes */
    static public final int FTR_ANSISDK_FINGPOS_UK = 0x00;    /* Unknown finger */
    static public final int FTR_ANSISDK_FINGPOS_RT = 0x01;    /* Right thumb */
    static public final int FTR_ANSISDK_FINGPOS_RI = 0x02;    /* Right index finger */
    static public final int FTR_ANSISDK_FINGPOS_RM = 0x03;    /* Right middle finger */
    static public final int FTR_ANSISDK_FINGPOS_RR = 0x04;    /* Right ring finger */
    static public final int FTR_ANSISDK_FINGPOS_RL = 0x05;    /* Right little finger */
    static public final int FTR_ANSISDK_FINGPOS_LT = 0x06;    /* Left thumb */
    static public final int FTR_ANSISDK_FINGPOS_LI = 0x07;    /* Left index finger */
    static public final int FTR_ANSISDK_FINGPOS_LM = 0x08;    /* Left middle finger */
    static public final int FTR_ANSISDK_FINGPOS_LR = 0x09;    /* Left ring finger */
    static public final int FTR_ANSISDK_FINGPOS_LL = 0x0A;    /* Left little finger */

    static public final float FTR_ANSISDK_MATCH_SCORE_LOW = 37;      /* FAR = 1% */
    static public final float FTR_ANSISDK_MATCH_SCORE_LOW_MEDIUM = 65;     /* FAR = 0.1% */
    static public final float FTR_ANSISDK_MATCH_SCORE_MEDIUM = 93;     /* FAR = 0.01% */
    static public final float FTR_ANSISDK_MATCH_SCORE_HIGH_MEDIUM = 121;     /* FAR = 0.001% */
    static public final float FTR_ANSISDK_MATCH_SCORE_HIGH = 146;     /* FAR = 0.0001% */
    static public final float FTR_ANSISDK_MATCH_SCORE_VERY_HIGH = 189;     /* FAR = 0 */

    private final int kDefaultDeviceInstance = 0;

    public AnsiSDKLib() {
        m_hDevice = 0;
        //m_glbIoCtxRef = 0;
        m_ImageWidth = m_ImageHeight = 0;
    }

    public int GetImageWidth() {
        return m_ImageWidth;
    }

    public int GetImageHeight() {
        return m_ImageHeight;
    }

    public int GetImageSize() {
        return m_ImageWidth * m_ImageHeight;
    }

    public int GetErrorCode() {
        return m_ErrorCode;
    }

    @SuppressLint("DefaultLocale")
    public String GetErrorMessage() {
        String strErrMsg;
        switch (m_ErrorCode) {
            case FTR_ERROR_NO_ERROR:
                strErrMsg = "OK";
                break;
            case FTR_ERROR_EMPTY_FRAME:
                strErrMsg = "Empty Frame";
                break;
            case FTR_ERROR_MOVABLE_FINGER:
                strErrMsg = "Moveable Finger";
                break;
            case FTR_ERROR_NO_FRAME:
                strErrMsg = "Fake Finger";
                break;
            case FTR_ERROR_HARDWARE_INCOMPATIBLE:
                strErrMsg = "Hardware Incompatible";
                break;
            case FTR_ERROR_FIRMWARE_INCOMPATIBLE:
                strErrMsg = "Firmware Incompatible";
                break;
            case FTR_ERROR_INVALID_AUTHORIZATION_CODE:
                strErrMsg = "Invalid Authorization Code";
                break;
            case FTR_ERROR_WRITE_PROTECT:
                strErrMsg = "Write Protect";
                break;
            case FTR_ERROR_NO_MORE_ITEMS:
                strErrMsg = "Device not connected";
                break;

            default:
                strErrMsg = String.format("Error code is %d", m_ErrorCode);
                break;
        }
        return strErrMsg;
    }


    static {
        System.loadLibrary("usb-1.0");
    }

    static {
        System.loadLibrary("ftrScanAPI");
    }

    static {
        System.loadLibrary("ftrAnsiSDK");
    }

    static {
        System.loadLibrary("ftrAnsiSDKAndroidJni");
    }

    private int m_ImageWidth;
    private int m_ImageHeight;
    private long m_hDevice;
    //private long m_glbIoCtxRef;
    private int m_ErrorCode;

}
