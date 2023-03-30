{-# LANGUAGE QuasiQuotes, OverloadedStrings #-}
module Util where

import           Data.List         as DL
import           Text.RE.TDFA
import           Text.Shakespeare.Text
import           Text.Printf
import           Data.Text         as T


combineLine :: Int
            -> T.Text
            -> T.Text
            -> T.Text
combineLine indent a x = [st|#{a}
#{(paddingRight "" indent)}#{x}|]

combinePrefix :: Int
              -> T.Text
              -> [T.Text]
              -> T.Text
combinePrefix indent prefix = T.intercalate (T.pack $ "\n" ++ (paddingLeft (T.unpack prefix) ((T.length prefix) + indent)))

combineNoHeadDelim :: Int
                   -> [T.Text]
                   -> T.Text
combineNoHeadDelim indent lines = if DL.null lines then ""
    else DL.foldl (combineLine indent) "" xs
        where xs          = [theHead] ++ withoutHead
              withoutHead = DL.drop 1 lines
              theHead     = (DL.head lines) *=~/ [ed|,///|]

combineNoTailDelim :: Int
                   -> [T.Text]
                   -> T.Text
combineNoTailDelim indent lines = if DL.null lines then ""
    else DL.foldl (combineLine indent) "" xs
        where xs          = withoutLast ++ [theLast]
              withoutLast = DL.reverse $ DL.drop 1 $ DL.reverse lines
              theLast     = (DL.last lines) *=~/ [ed|,///|]

paddingLeft :: String -> Int -> String
paddingLeft s n =
    printf fmt s where
        fmt = printf "%%%d.%ds" n n

paddingRight :: String -> Int -> String
paddingRight s n =
    DL.reverse rpl where
        rs  = DL.reverse s
        fmt = printf "%%%d.%ds" n n
        rpl = printf fmt rs


