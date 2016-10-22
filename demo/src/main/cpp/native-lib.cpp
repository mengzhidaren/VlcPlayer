#include <jni.h>
#include <string>
extern "C"
jstring
Java_com_videolist_yyl_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    std ::string demo="ddddddd";
    demo.append(hello);
    printf(demo.c_str(),"dd");

    return env->NewStringUTF(demo.c_str());
}
