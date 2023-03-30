{-# LANGUAGE QuasiQuotes, OverloadedStrings #-}
module GenDSL(
            gen
          , getGens
          , gensFromOpts
          ) where

import           System.IO        (stderr, hPutStrLn)
import           Control.Monad    (when)
import           Data.Maybe       (fromMaybe)
import qualified CmdLine          as CL
import qualified Metadata         as Meta
import qualified JavaMessage      as JavaMessage

gen :: CL.Options
    -> Meta.Model
    -> IO ()
gen opts model = do
    let gens = getGens opts
    if null gens
        then hPutStrLn stderr "no generators enabled."
        else mapM_ (\ g -> g opts model) gens

getGens :: CL.Options
        -> [CL.Options -> Meta.Model -> IO ()]
getGens opts = justGens $ removeNothing maybeGens
    where
        justGens      = map (fromMaybe (error "Something bad happend..."))
        removeNothing = filter (\ g -> case g of
            Just _ -> True
            Nothing -> False)
        maybeGens     = map (\ g -> g opts) $ gensFromOpts

gensFromOpts :: [CL.Options -> Maybe (CL.Options -> Meta.Model -> IO ())]
gensFromOpts =
    [ \ opts -> if CL.genMessage opts then Just JavaMessage.gen else Nothing
    ]

