{-# LANGUAGE QuasiQuotes #-}
{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE BangPatterns #-}
{-# LANGUAGE BinaryLiterals #-}
{-# LANGUAGE DeriveGeneric #-}
-- since ghc 8.0.1 {-# LANGUAGE Strict #-}
-- since ghc 8.0.1 {-# LANGUAGE StrictData #-}
module Metadata where

import qualified Data.Text              as T
import qualified Data.Map               as Map
import           GHC.Generics              (Generic)


data EnumItem
    = EnumItem
    { enumName    :: !T.Text
    , enumValue   :: Int
    , enumComment :: !T.Text
    }
    deriving (Eq, Show, Generic)

data Field
    = Int8Field
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | UInt8Field
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | Int16Field
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | UInt16Field
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | Int32Field
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | UInt32Field
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | Int64Field
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | UInt64Field
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | Float32Field
    { fieldName    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | Float64Field
    { fieldName    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | ByteStringField
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | StringField
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | NTStringField
    { fieldName    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldComment :: !T.Text
    }
    | EnumerateField
    { fieldName    :: !T.Text
    , fieldType    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldValue   :: !T.Text
    , fieldEnums   :: ![EnumItem]
    , fieldComment :: !T.Text
    }
    | EntityField
    { fieldName    :: !T.Text
    , fieldType    :: !T.Text
    , fieldSize    :: !T.Text
    , fieldComment :: !T.Text
    }
    deriving (Eq, Show, Generic)

data Entity
    = Message
    { entityId      :: !T.Text
    , entityName    :: !T.Text
    , entityFields  :: ![Field] -- order by versionNo
    , entityComment :: !T.Text
    }
    | Struct
    { entityId      :: !T.Text
    , entityName    :: !T.Text
    , entityFields  :: ![Field] -- order by versionNo
    , entityComment :: !T.Text
    }
    | State 
    { entityId      :: !T.Text
    , entityName    :: !T.Text
    , entityFields  :: ![Field] -- order by versionNo
    , entityComment :: !T.Text
    }
    deriving (Eq, Show, Generic)

data Model = Model
    { namespace    :: !T.Text
    , headerFields :: ![Field]
    , tailFields   :: ![Field]
    , entities     :: ![Entity]
    , enumerates   :: ![Field]
    }

