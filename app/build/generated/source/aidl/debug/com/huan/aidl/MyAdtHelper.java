/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspaceAS\\MyADT\\app\\src\\main\\aidl\\com\\huan\\aidl\\MyAdtHelper.aidl
 */
package com.huan.aidl;
//MyADT_AIDL

public interface MyAdtHelper extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.huan.aidl.MyAdtHelper
{
private static final java.lang.String DESCRIPTOR = "com.huan.aidl.MyAdtHelper";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.huan.aidl.MyAdtHelper interface,
 * generating a proxy if needed.
 */
public static com.huan.aidl.MyAdtHelper asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.huan.aidl.MyAdtHelper))) {
return ((com.huan.aidl.MyAdtHelper)iin);
}
return new com.huan.aidl.MyAdtHelper.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_sendMessage:
{
data.enforceInterface(DESCRIPTOR);
com.huan.bean.MyLogBean _arg0;
if ((0!=data.readInt())) {
_arg0 = com.huan.bean.MyLogBean.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.sendMessage(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.huan.aidl.MyAdtHelper
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
//boolean sendMessage(String logtext);

@Override public boolean sendMessage(com.huan.bean.MyLogBean myLogBean) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((myLogBean!=null)) {
_data.writeInt(1);
myLogBean.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_sendMessage, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_sendMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
//boolean sendMessage(String logtext);

public boolean sendMessage(com.huan.bean.MyLogBean myLogBean) throws android.os.RemoteException;
}