package com.sk01.app;

import com.sk01.storage.Storage;
import com.sk01.storage.Search;
import com.sk01.storage.Create;
import com.sk01.storage.Operations;
import com.sk01.StorageManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class MainApp {

    public static void main(String[] args) {

        // com.localimpl.Local && com.driveimpl.GoogleDrive
        try {
            Class.forName("com.sk01.localImpl.Local");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Storage storage = StorageManager.getStorage();
        Create create= StorageManager.getCreate();
        Operations operations = StorageManager.getOperations();
        Search search = StorageManager.getSearch();

        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("If you need some instructions, please type command help");
        System.out.println("If you want to quit application, please type command exit");

        while (true) {
            command = scanner.nextLine();
            if (command.equals("exit")) {
                return;
            }

            if (command.equals("help")) {
                help();
                continue;
            }

            String[] commArray = command.split(" ");
            try {

                //STORAGE
                if (commArray[0].equals("createstorage") && commArray.length == 3) {
                    storage.createStorage(commArray[1], commArray[2]);
                    continue;
                }

                if (commArray[0].equals("config") && commArray.length >= 3 && commArray.length <= 7) {
                    String maxSize = null;
                    String maxNOF = null;
                    List<String> unsupportedExtension = null;
                    for (int i = 1; i < commArray.length; i += 2) {
                        if (commArray[i].equals("-maxsize")) {
                            maxSize = commArray[i+1];
                        }

                        if (commArray[i].equals("-maxnof")) {
                            maxNOF = commArray[i+1];
                        }

                        if (commArray[i].equals("-une")) {
                            unsupportedExtension = new ArrayList<>(Arrays.asList(commArray[i + 1].split(",")));
                        }
                    }

                    storage.configure(maxSize, maxNOF, unsupportedExtension);
                    continue;
                }


                //CREATE
                if (commArray[0].equals("createdir") && commArray.length == 2) {
                    create.createDir(commArray[1]);
                    continue;
                }

                if (commArray[0].equals("createdirs") && commArray.length == 3) {
                    create.createDirs(commArray[1],Integer.parseInt(commArray[2]));
                    continue;
                }

                if (commArray[0].equals("createfile") && commArray.length == 2) {
                    create.createFiles(commArray[1]);
                    continue;
                }

                if (commArray[0].equals("createfile") && commArray.length == 3) {
                    create.createFiles(commArray[1], commArray[2]);
                    continue;
                }


                //getFiles myDrive
                //OPERATIONS
                if ( commArray[0].equals("getfiles")) {
                    //String path = commArray[1].equals("/root") ? "" : commArray[1];
                    printFiles(search.getAllFiles("."));
                    continue;
                }

                /*if (commArray[0].equals("getdirs") && commArray.length == 2) {
                    String path = commArray[1].equals("/root") ? "" : commArray[1];
                    printFiles(operations.getAllDirectories(path));
                    continue;
                }

                if (commArray[0].equals("getall") && commArray.length == 2) {
                    String path = commArray[1].equals("/root") ? "" : commArray[1];
                    printFiles(operations.getAllFilesRecursive(path));
                    continue;
                }

                if (commArray[0].equals("download") && commArray.length == 2) {
                    operations.download(commArray[1]);
                    continue;
                }

                if (commArray[0].equals("upload") && commArray.length == 3) {
                    String toPath = commArray[2].equals("/root") ? "" : commArray[2];
                    operations.uploadFile(commArray[1], toPath);
                    continue;
                }

                if (commArray[0].equals("move") && commArray.length == 3) {
                    String toPath = commArray[2].equals("/root") ? "" : commArray[2];
                    operations.moveFile(commArray[1], toPath);
                    continue;
                }

                if (commArray[0].equals("getfileswe") && commArray.length == 3) {
                    String path = commArray[1].equals("/root") ? "" : commArray[1];
                    printFiles(operations.getAllFilesWithExtention(operations.getAllFiles(path), commArray[2]));
                    continue;
                }

                if (commArray[0].equals("sort") && commArray.length == 3) {
                    String path = commArray[1].equals("/root") ? "" : commArray[1];
                    printFiles(operations.getSortedBy(operations.getAllFiles(path), commArray[2]));
                    continue;
                }

                if (commArray[0].equals("between") && commArray.length == 4) {
                    String path = commArray[1].equals("/root") ? "" : commArray[1];
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date startDate = sdf.parse(commArray[2]);
                    Date endDate = sdf.parse(commArray[3]);
                    printFiles(operations.getInBetweenDates(operations.getAllFiles(path), startDate, endDate));
                    continue;
                }

                System.out.println("Incorrect command, for more information use command help");*/

            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static void help() {
        System.out.println("INFO:");
        System.out.println("    Path within storage must be relative");
        System.out.println("    Empty path is marked as '.'");
//        System.out.println("    Path outside of storage must be absolute");
//        System.out.println("    Root path must be entered as - /root");
        System.out.println("    When creating and logging to storage path must be absolute");
        System.out.println("    Dates format: dd/MM/yyyy");

        System.out.println("STORAGE:");
        System.out.println("    Create Storage - createstorage path storage_name");
        System.out.println("    Configure - config -maxsize nn -maxnof nn -une e1,e2...");

        System.out.println("CREATE:");
        System.out.println("    Create Directory - createdir path");
        System.out.println("    Create Directory - createdir path dir_name");
        System.out.println("    Create Directories - createdirs path number_of_dirs");
        System.out.println("    Create File - createfile path");
        System.out.println("    Create File - createfile path file_name");
        System.out.println("    Create Files - createfiles path number_of_files");

        System.out.println("SEARCH:");
        System.out.println("    Get File - getfiles path");
        System.out.println("    Get All Files - getallfiles");
        System.out.println("    Get All Files - getallfiles path");
        System.out.println("    Get Files That Contain String - getfilespodstring podstring");
        System.out.println("    Get All Files With Extention - getfileswe path e");
        System.out.println("    Contains Files? - getContains path file_names");
        System.out.println("    Get Directory - getdir path");
        System.out.println("    Get Files In Between Dates - getfilesbetween path start_date end_date");

        System.out.println("OPERATIONS:");
        System.out.println("    Delete Directory - deletedir path");
        System.out.println("    Delete File - deletefile path");
        System.out.println("    Delete All - deleteall root_path");
        System.out.println("    Download File - download from_path to_path");
        System.out.println("    Move File - move from_path to_path");
//        System.out.println("    Get Sort By - sort path criteria -> sortByName, sortByDate, sortByModification and combinations ex. sortByName-sortByDate");
    }

    private static void printFiles(List<File> metadataList) {
        for (File metadata: metadataList) {
            System.out.println(metadata);
        }
    }
}
