# qiniu
使用七牛云做图片服务器

# 使用步骤：
在浏览器输入：localhost:8099/index 后打开上传页面，由于目前文件类型已经在FileUtil类中限定，所以只能按要求选择合适的文件上传。

```java
public class FileUtil {
    //图片允许的后缀扩展名
    public static String[] IMAGE_FILE_EXID = new String[]{"png", "bmp", "jpg", "jpeg","pdf"};
    public static boolean isFileAllowed(String fileName){
        for (String ext : IMAGE_FILE_EXID) {
            if (ext.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
```
上传完成后会返回文件在七牛云上的存储位置，将该地址粘贴到浏览器中即可进行检验，能正常打开文件，就说明文件上传成功。
