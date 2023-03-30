{-# LANGUAGE QuasiQuotes, OverloadedStrings #-}
module JavaMessage (gen) where

import           System.IO
import           System.Exit
import           System.Directory
import           System.Environment
import           System.FilePath
import           Control.Monad        (when)
import           Data.List         as DL
import           Data.Maybe           (fromMaybe)
import           Text.Printf
import           Text.RE.TDFA
import qualified Data.Map          as M
import           Text.Shakespeare.Text
import qualified Data.Text.Lazy.IO as TLIO
import           Data.Text         as T
import           Data.Text.Lazy    as TL
import           Control.Monad
import qualified CmdLine           as CL
import qualified Util              as Util
import qualified Metadata          as Meta


gen :: CL.Options
    -> Meta.Model
    -> IO ()
gen opts model = do
    let namespace = T.concat ["com.github.apuex.", T.replace "::" "." $ Meta.namespace model]
    let p = T.replace "." "/" $ namespace
    let fp = (T.unpack $ CL.outputDir opts) </> (T.unpack $ p)
    createDirectoryIfMissing True fp
    let ns = T.splitOn "." namespace
    let javaName = DL.concat [fp, "/Message.java"]
    withFile javaName WriteMode $ \ h -> do
        hPrintf h "%s\n" $ genMessage fp namespace model
    mapM_ (genEnum fp namespace) $ Meta.enumerates model 
    mapM_ (genEntity fp namespace model) $ Meta.entities model 
    mapM_ (genEntityCodec fp namespace model) $ Meta.entities model 

genEnum :: String
        -> T.Text
        -> Meta.Field
        -> IO ()
genEnum fp ns field = case field of
    Meta.EnumerateField n t s v es c -> do
        let fileName = DL.concat [fp, "/", T.unpack t, ".java"]
        let content = [st|/*
 * Copyright (c) 2021-2023 WINCOM.
 * Copyright (c) 2021-2023 Wangxy <xtwxy@hotmail.com>
 *
 * All rights reserved. 
 *
 * This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License 2.0.
 *
 * Contributors:
 *   Wangxy - initial implementation and documentation.
*/

package #{ns};

/**
 * #{c}
 *
 * @author Wangxy
 */
public enum #{t} {
    #{Util.combinePrefix 4 ", " $ DL.map genEnumItem es}
    ;

    #{t}(int v) {
        this.value = v;
    }

    public int getValue() {
        return this.value;
    }

    public static #{t} fromValue(int v) {
        switch(v) {
        #{Util.combinePrefix 8 "" $ DL.map genEnumFromValue es}
        default: throw new IllegalArgumentException(String.format("%d is an invalid enum value.", v));
        }
    }
 
    private final int value;
}
|]
        withFile fileName WriteMode $ \ h -> do
            hPrintf h "%s" content
            return ()
    _ -> return ()

genEnumItem :: Meta.EnumItem
            -> T.Text
genEnumItem (Meta.EnumItem n v c) = [st|#{n}(#{v}) //#{c}|]

genEnumFromValue :: Meta.EnumItem
                 -> T.Text
genEnumFromValue (Meta.EnumItem n v c) = [st|case #{v}: return #{n};|]

genEntity :: String
          -> T.Text
          -> Meta.Model
          -> Meta.Entity
          -> IO ()
genEntity fp ns model entity = do
    let fileName = DL.concat [fp, "/", T.unpack $ Meta.entityName entity, ".java"]
    let content = genEntityContent fp ns model entity
    withFile fileName WriteMode $ \ h -> do
        hPrintf h "%s" content
        return ()
    return ()

genEntityContent :: String
                 -> T.Text
                 -> Meta.Model
                 -> Meta.Entity
                 -> T.Text
genEntityContent fp ns model (Meta.Message t n fields c) = [st|/*
 * Copyright (c) 2021-2023 WINCOM.
 * Copyright (c) 2021-2023 Wangxy <xtwxy@hotmail.com>
 *
 * All rights reserved. 
 *
 * This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License 2.0.
 *
 * Contributors:
 *   Wangxy - initial implementation and documentation.
*/

package #{ns};

/**
 * #{c}
 *
 * @author Wangxy
 */
public class #{n} extends Message {
    private static final long serialVersionUID = 1L;
    #{genMsgDefConstructor t n fields c}
    public #{n}
    ( #{Util.combinePrefix 4 ", " $ DL.map genParam fields}
    ) {
        super(#{T.replace "::" "." t});
        #{Util.combinePrefix 8 "" $ DL.map (genInitializer "") fields}
    }

    public #{n}
    ( #{Util.combinePrefix 4 ", " $ DL.map genParam $ ((DL.last $ DL.take 3 $ Meta.headerFields model) : fields)}
    ) {
        super(SerialNo, #{T.replace "::" "." t});
        #{Util.combinePrefix 8 "" $ DL.map (genInitializer "") fields}
    }

    @Override
    public boolean equals(Object o) {
        #{n} r = null;
        if(o instanceof #{n}) {
            r = (#{n}) o;
        } else {
            return false;
        }

        boolean result =
            ( #{Util.combinePrefix 12 "&& " $ DL.map (genEquals "") $ DL.concat [Meta.headerFields model, fields, Meta.tailFields model]}
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("#{n} { ")
            #{Util.combinePrefix 12 ".append(\", \")" $ DL.map (genToString "") $ DL.concat [Meta.headerFields model, fields, Meta.tailFields model]}
            .append(" }");

        return builder.toString();
    }

    #{Util.combinePrefix 4 "" $ DL.map (genField "") fields}
}

|]

genEntityContent fp ns model (Meta.Struct t n fields c) = [st|/*
 * Copyright (c) 2021-2023 WINCOM.
 * Copyright (c) 2021-2023 Wangxy <xtwxy@hotmail.com>
 *
 * All rights reserved. 
 *
 * This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License 2.0.
 *
 * Contributors:
 *   Wangxy - initial implementation and documentation.
*/

package #{ns};

import java.io.Serializable;

/**
 * #{c}
 *
 * @author Wangxy
 */
public class #{n} implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        #{n} r = null;
        if(o instanceof #{n}) {
            r = (#{n}) o;
        } else {
            return false;
        }

        boolean result =
            ( #{Util.combinePrefix 12 "&& " $ DL.map (genEquals "") fields}
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("#{n} { ")
            #{Util.combinePrefix 12 ".append(\", \")" $ DL.map (genToString "") fields}
            .append(" }");

        return builder.toString();
    }

    #{Util.combinePrefix 4 "" $ DL.map (genField "") fields}
}

|]

genEntityContent fp ns model (Meta.State t n fields c) = [st|/*
 * Copyright (c) 2021-2023 WINCOM.
 * Copyright (c) 2021-2023 Wangxy <xtwxy@hotmail.com>
 *
 * All rights reserved. 
 *
 * This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License 2.0.
 *
 * Contributors:
 *   Wangxy - initial implementation and documentation.
*/

package #{ns};

/**
 * #{c}
 *
 * @author Wangxy
 */
public class #{n} implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        #{n} r = null;
        if(o instanceof #{n}) {
            r = (#{n}) o;
        } else {
            return false;
        }

        boolean result =
            ( #{Util.combinePrefix 12 "&& " $ DL.map (genEquals "") fields}
            );

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
            .append("#{n} { ")
            #{Util.combinePrefix 12 ".append(\", \")" $ DL.map (genToString "") fields}
            .append(" }");

        return builder.toString();
    }

    #{Util.combinePrefix 4 "" $ DL.map (genField "") fields}
}

|]

genMsgDefConstructor :: T.Text
                     -> T.Text
                     -> [Meta.Field]
                     -> T.Text
                     -> T.Text
genMsgDefConstructor t n fields c = if DL.null fields 
    then [st||]
    else [st|
    public #{n}() {
        super(#{T.replace "::" "." t});
    }
|]

genMessage :: String
           -> T.Text
           -> Meta.Model
           -> T.Text
genMessage fp ns model = [st|package #{ns};

import java.io.Serializable;

/**
 * Parent class for all PKType of messages.
 *
 * @author Wangxy
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int MESSAGE_HEADER = 0x7E7C6B5A;

    public Message
    ( EnumPKType PKType
    ) {
        this.Header = MESSAGE_HEADER;
        this.Length = 0;
        this.SerialNo = 0;
        this.PKType = PKType;
        this.CRC16 = 0;
    }

    public Message
    ( int SerialNo
    , EnumPKType PKType
    ) {
        this.Header = MESSAGE_HEADER;
        this.Length = 0;
        this.SerialNo = SerialNo;
        this.PKType = PKType;
        this.CRC16 = 0;
    }

    public int Header; // Message header, 0x7E7C6B5A.
    public int Length; // Message length.
    public int SerialNo; // Message serial number.
    public EnumPKType PKType; // 报文定义
    public short CRC16; // Message CRC16.
}

|]

genEntityCodec :: String
               -> T.Text
               -> Meta.Model
               -> Meta.Entity
               -> IO ()
genEntityCodec fp ns model entity = do
    let fileName = DL.concat [fp, "/", T.unpack $ Meta.entityName entity, "Codec.java"]
    let content = genEntityCodecContent fp ns model entity
    withFile fileName WriteMode $ \ h -> do
        hPrintf h "%s" content
        return ()
    return ()

genEntityCodecContent :: String
                 -> T.Text
                 -> Meta.Model
                 -> Meta.Entity
                 -> T.Text
genEntityCodecContent fp ns model (Meta.Message t n fields c) = [st|/*
 * Copyright (c) 2021-2023 WINCOM.
 * Copyright (c) 2021-2023 Wangxy <xtwxy@hotmail.com>
 *
 * All rights reserved. 
 *
 * This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License 2.0.
 *
 * Contributors:
 *   Wangxy - initial implementation and documentation.
*/

package #{ns};

import java.nio.ByteBuffer;

/**
 * #{c}
 *
 * @author Wangxy
 */
public class #{n}Codec {

    public void encode(ByteBuffer buf, #{n} v) {
        final int initialPos = buf.position();
        // Message HEAD - envelope fields
        #{Util.combinePrefix 8 "" $ DL.map (genEncode "") $ Meta.headerFields model}
        // Message CONTENT BEGIN 
        #{Util.combinePrefix 8 "" $ DL.map (genEncode "") fields}
        // Message CONTENT END 
        // Message TAIL - envelope fields
        #{Util.combinePrefix 8 "" $ DL.map (genEncode "") $ Meta.tailFields model}
        final int pos = buf.position();
        // Message LENGTH - envelope fields
        v.Length = pos - initialPos;
        buf.position(initialPos + 4);
        buf.putInt(v.Length);
        buf.position(pos - 2);
        v.CRC16 = Util.CRC16(buf.array(), initialPos, pos - 2);
        buf.putShort(v.CRC16);
    }

    public #{n} decode(ByteBuffer buf) {
        #{n} v = new #{n}();
        // Message HEAD - envelope fields
        #{Util.combinePrefix 8 "" $ DL.map (genDecode "") $ Meta.headerFields model}
        // Message CONTENT BEGIN 
        #{Util.combinePrefix 8 "" $ DL.map (genDecode "") fields}
        // Message CONTENT END 
        // Message TAIL - envelope fields
        #{Util.combinePrefix 8 "" $ DL.map (genDecode "") $ Meta.tailFields model}
        return v;
    }

    #{Util.combinePrefix 4 "" $ DL.map (genFieldCodec "") $ DL.filter isEntityField fields}
}

|]

genEntityCodecContent fp ns model (Meta.Struct t n fields c) = [st|/*
 * Copyright (c) 2021-2023 WINCOM.
 * Copyright (c) 2021-2023 Wangxy <xtwxy@hotmail.com>
 *
 * All rights reserved. 
 *
 * This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License 2.0.
 *
 * Contributors:
 *   Wangxy - initial implementation and documentation.
*/

package #{ns};

import java.nio.ByteBuffer;

/**
 * #{c}
 *
 * @author Wangxy
 */
public class #{n}Codec {

    public void encode(ByteBuffer buf, #{n} v) {
        #{Util.combinePrefix 8 "" $ DL.map (genEncode "") fields}
    }

    public #{n} decode(ByteBuffer buf) {
        #{n} v = new #{n}();
        #{Util.combinePrefix 8 "" $ DL.map (genDecode "") fields}
        return v;
    }

    #{Util.combinePrefix 4 "" $ DL.map (genFieldCodec "") $ DL.filter isEntityField fields}
}

|]

genEntityCodecContent fp ns model (Meta.State t n fields c) = [st|/*
 * Copyright (c) 2021-2023 WINCOM.
 * Copyright (c) 2021-2023 Wangxy <xtwxy@hotmail.com>
 *
 * All rights reserved. 
 *
 * This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License 2.0.
 *
 * Contributors:
 *   Wangxy - initial implementation and documentation.
*/

package #{ns};

import java.nio.ByteBuffer;

/**
 * #{c}
 *
 * @author Wangxy
 */
public class #{n}Codec {

    public void encode(ByteBuffer buf, #{n} v) {
        #{Util.combinePrefix 8 "" $ DL.map (genEncode "") fields}
    }

    public #{n} decode(ByteBuffer buf) {
        #{n} v = new #{n}();
        #{Util.combinePrefix 8 "" $ DL.map (genDecode "") fields}
        return v;
    }

    #{Util.combinePrefix 4 "" $ DL.map (genFieldCodec "") $ DL.filter isEntityField fields}
}

|]

genField :: T.Text
         -> Meta.Field
         -> T.Text
genField prefix field = case field of
    Meta.Int8Field n s v c -> [st|public byte #{prefix}#{n}; // #{c}|]
    Meta.UInt8Field n s v c -> [st|public byte #{prefix}#{n}; // #{c}|]
    Meta.Int16Field n s v c -> [st|public short #{prefix}#{n}; // #{c}|]
    Meta.UInt16Field n s v c -> [st|public short #{prefix}#{n}; // #{c}|]
    Meta.Int32Field n s v c -> [st|public int #{prefix}#{n}; // #{c}|]
    Meta.UInt32Field n s v c -> [st|public int #{prefix}#{n}; // #{c}|]
    Meta.Int64Field n s v c -> [st|public long #{prefix}#{n}; // #{c}|]
    Meta.UInt64Field n s v c -> [st|public long #{prefix}#{n}; // #{c}|]
    Meta.Float32Field n v c -> [st|public float #{prefix}#{n}; // #{c}|]
    Meta.Float64Field n v c -> [st|public double #{prefix}#{n}; // #{c}|]
    Meta.ByteStringField n s v c -> [st|public ByteBuffer #{prefix}#{n}; // #{c}|]
    Meta.StringField n s v c -> [st|public String #{prefix}#{n}; // #{c}|]
    Meta.NTStringField n s v c -> [st|public String #{prefix}#{n}; // #{c}|]
    Meta.EnumerateField n t s v es c -> [st|public #{t} #{prefix}#{n}; // #{c}|]
    Meta.EntityField n t v c -> [st|public #{t} #{prefix}#{n}; // #{c}|]

isEntityField :: Meta.Field
              -> Bool
isEntityField (Meta.EntityField _ _ _ _) = True

isEntityField _ = False
 
genFieldCodec :: T.Text
              -> Meta.Field
              -> T.Text
genFieldCodec prefix field = case field of
    Meta.EntityField n t v c -> [st|public #{t}Codec #{prefix}#{n}Codec = new #{t}Codec(); // #{c}|]
    _ -> [st||]

genParam :: Meta.Field
         -> T.Text
genParam field = case field of
    Meta.Int8Field n s v c -> [st|byte #{n}|]
    Meta.UInt8Field n s v c -> [st|byte #{n}|]
    Meta.Int16Field n s v c -> [st|short #{n}|]
    Meta.UInt16Field n s v c -> [st|short #{n}|]
    Meta.Int32Field n s v c -> [st|int #{n}|]
    Meta.UInt32Field n s v c -> [st|int #{n}|]
    Meta.Int64Field n s v c -> [st|long #{n}|]
    Meta.UInt64Field n s v c -> [st|long #{n}|]
    Meta.Float32Field n v c -> [st|float #{n}|]
    Meta.Float64Field n v c -> [st|double #{n}|]
    Meta.ByteStringField n s v c -> [st|ByteBuffer #{n}|]
    Meta.StringField n s v c -> [st|String #{n}|]
    Meta.NTStringField n s v c -> [st|String #{n}|]
    Meta.EnumerateField n t s v es c -> [st|#{t} #{n}|]
    Meta.EntityField n t v c -> [st|#{t} #{n}|]


genDefaultInitializer :: T.Text
                      -> Meta.Field
                      -> T.Text
genDefaultInitializer prefix field = case field of
    Meta.Int8Field n s v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.UInt8Field n s v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.Int16Field n s v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.UInt16Field n s v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.Int32Field n s v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.UInt32Field n s v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.Int64Field n s v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.UInt64Field n s v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.Float32Field n v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.Float64Field n v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.ByteStringField n s v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.StringField n s v c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.NTStringField n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.EnumerateField n t s v es c -> [st|this.#{prefix}#{n} = #{v};|]
    Meta.EntityField n t v c -> [st|this.#{prefix}#{n} = #{v};|]

genInitializer :: T.Text
               -> Meta.Field
               -> T.Text
genInitializer prefix field = case field of
    Meta.Int8Field n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.UInt8Field n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.Int16Field n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.UInt16Field n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.Int32Field n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.UInt32Field n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.Int64Field n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.UInt64Field n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.Float32Field n v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.Float64Field n v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.ByteStringField n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.StringField n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.NTStringField n s v c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.EnumerateField n t s v es c -> [st|this.#{prefix}#{n} = #{n};|]
    Meta.EntityField n t v c -> [st|this.#{prefix}#{n} = #{n};|]

genEncode :: T.Text
          -> Meta.Field
          -> T.Text
genEncode prefix field = case field of
    Meta.Int8Field n s v c -> [st|buf.put(v.#{prefix}#{n});|]
    Meta.UInt8Field n s v c -> [st|buf.put(v.#{prefix}#{n});|]
    Meta.Int16Field n s v c -> [st|buf.putShort(v.#{prefix}#{n});|]
    Meta.UInt16Field n s v c -> [st|buf.putShort(v.#{prefix}#{n});|]
    Meta.Int32Field n s v c -> [st|buf.putInt(v.#{prefix}#{n});|]
    Meta.UInt32Field n s v c -> [st|buf.putInt(v.#{prefix}#{n});|]
    Meta.Int64Field n s v c -> [st|buf.putLong(v.#{prefix}#{n});|]
    Meta.UInt64Field n s v c -> [st|buf.putLong(v.#{prefix}#{n});|]
    Meta.Float32Field n v c -> [st|buf.putFloat(v.#{prefix}#{n});|]
    Meta.Float64Field n v c -> [st|buf.putDouble(v.#{prefix}#{n});|]
    Meta.ByteStringField n s v c -> [st|Util.encodeString(buf, v.#{prefix}#{n}, #{T.replace "::" "." s});|]
    Meta.StringField n s v c -> [st|Util.encodeString(buf, v.#{prefix}#{n}, #{T.replace "::" "." s});|]
    Meta.NTStringField n s v c -> [st|Util.encodeString(buf, v.#{prefix}#{n}, #{T.replace "::" "." s});|]
    Meta.EnumerateField n t s v es c -> [st|buf.putInt(v.#{prefix}#{n}.getValue());|]
    Meta.EntityField n t v c -> [st|this.#{prefix}#{n}Codec.encode(buf, v.#{prefix}#{n});|]

genDecode :: T.Text
          -> Meta.Field
          -> T.Text
genDecode prefix field = case field of
    Meta.Int8Field n s v c -> [st|v.#{prefix}#{n} = buf.get();|]
    Meta.UInt8Field n s v c -> [st|v.#{prefix}#{n} = buf.get();|]
    Meta.Int16Field n s v c -> [st|v.#{prefix}#{n} = buf.getShort();|]
    Meta.UInt16Field n s v c -> [st|v.#{prefix}#{n} = buf.getShort();|]
    Meta.Int32Field n s v c -> [st|v.#{prefix}#{n} = buf.getInt();|]
    Meta.UInt32Field n s v c -> [st|v.#{prefix}#{n} = buf.getInt();|]
    Meta.Int64Field n s v c -> [st|v.#{prefix}#{n} = buf.getLong();|]
    Meta.UInt64Field n s v c -> [st|v.#{prefix}#{n} = buf.getLong();|]
    Meta.Float32Field n v c -> [st|v.#{prefix}#{n} = buf.getFloat();|]
    Meta.Float64Field n v c -> [st|v.#{prefix}#{n} = buf.getDouble();|]
    Meta.ByteStringField n s v c -> [st|v.#{prefix}#{n} = Util.decodeString(buf, #{T.replace "::" "." s});|]
    Meta.StringField n s v c -> [st|v.#{prefix}#{n} = Util.decodeString(buf, #{T.replace "::" "." s});|]
    Meta.NTStringField n s v c -> [st|v.#{prefix}#{n} = Util.decodeString(buf, #{T.replace "::" "." s});|]
    Meta.EnumerateField n t s v es c -> [st|v.#{prefix}#{n} = #{t}.fromValue(buf.getInt());|]
    Meta.EntityField n t v c -> [st|v.#{prefix}#{n} = this.#{prefix}#{n}Codec.decode(buf);|]



genToString :: T.Text
            -> Meta.Field
            -> T.Text
genToString prefix field = case field of
    Meta.Int8Field n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.UInt8Field n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.Int16Field n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.UInt16Field n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.Int32Field n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.UInt32Field n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.Int64Field n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.UInt64Field n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.Float32Field n v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.Float64Field n v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.ByteStringField n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.StringField n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.NTStringField n s v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.EnumerateField n t s v es c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]
    Meta.EntityField n t v c -> [st|.append("#{prefix}#{n}=").append(this.#{prefix}#{n})|]

genEquals :: T.Text
          -> Meta.Field
          -> T.Text
genEquals prefix field = case field of
    Meta.Int8Field n s v c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.UInt8Field n s v c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.Int16Field n s v c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.UInt16Field n s v c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.Int32Field n s v c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.UInt32Field n s v c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.Int64Field n s v c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.UInt64Field n s v c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.Float32Field n v c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.Float64Field n v c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.ByteStringField n s v c -> [st|this.#{prefix}#{n}.equals(r.#{prefix}#{n})|]
    Meta.StringField n s v c -> [st|this.#{prefix}#{n}.equals(r.#{prefix}#{n})|]
    Meta.NTStringField n s v c -> [st|this.#{prefix}#{n}.equals(r.#{prefix}#{n})|]
    Meta.EnumerateField n t s v es c -> [st|this.#{prefix}#{n} == r.#{prefix}#{n}|]
    Meta.EntityField n t v c -> [st|this.#{prefix}#{n}.equals(r.#{prefix}#{n})|]


