{-# LANGUAGE QuasiQuotes, OverloadedStrings #-}
module Main(main) where

import           System.Console.GetOpt
import           System.IO
import           System.Environment
import           Control.Monad (when)
import qualified MessageModel      as Model
import qualified Metadata          as Meta
import qualified GenDSL            as G
import qualified CmdLine           as CL


main :: IO ()
main = do
    progName <- getProgName
    args     <- getArgs
    (opts, files) <- CL.compileOpts progName args
    if CL.printVersion opts
        then putStrLn $ CL.versionInfo progName
        else G.gen opts Model.model 

