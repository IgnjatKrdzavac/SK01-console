package com.sk01.app;

import com.sk01.storage.Storage;
import com.sk01.storage.Search;
import com.sk01.storage.Create;
import com.sk01.storage.Operations;
import com.sk01.StorageManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;


public class MainApp {

    public static void main(String[] args) {

        try {
            //Class.forName("com.sk01.driveimpl.GoogleDrive");
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

        System.out.println("Use command 'help' for instructions.");
        System.out.println("To quit application use command 'exit'");

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

                if (commArray[0].equals("createdir") && commArray.length == 3) {
                    create.createDir(commArray[1],commArray[2]);
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
                if (commArray[0].equals("createfiles") && commArray.length == 3) {
                    create.createFiles(commArray[1], Integer.parseInt(commArray[2]));
                    continue;
                }

                //OPERATIONS
                if (commArray[0].equals("deletefile") && commArray.length == 2) {
                    operations.deleteFile(commArray[1]);
                    continue;
                }
                if (commArray[0].equals("deletedir") && commArray.length == 2) {
                    operations.deleteDir(commArray[1]);
                    continue;
                }
                if (commArray[0].equals("deleteall") && commArray.length == 2) {
                    operations.deleteAll(commArray[1]);
                    continue;
                }
                if (commArray[0].equals("movefiles") && commArray.length == 3) {
                    operations.moveFiles(commArray[1],commArray[2]);
                    continue;
                }
                if (commArray[0].equals("downloadfile") && commArray.length == 3) {
                    operations.downloadFile(commArray[1],commArray[2]);
                    continue;
                }
                if (commArray[0].equals("rename") && commArray.length == 3) {
                    operations.rename(commArray[1], commArray[2]);
                    continue;
                }

                //SEARCH
                if (commArray[0].equals("getfile") && commArray.length == 2) {
                    printFile(search.getFile(commArray[1]));
                    continue;
                }

                if (commArray[0].equals("getdir") && commArray.length == 2) {
                    System.out.println((search.getDir(commArray[1])));
                    continue;
                }

                if (commArray[0].equals("getallfiles") && commArray.length == 1) {
                    printFiles(search.getAllFiles(commArray[1]));
                    continue;
                }

                if (commArray[0].equals("getallfiles") && commArray.length == 2) {
                    System.out.println((search.getAllFiles(commArray[1])));
                    continue;
                }

                if (commArray[0].equals("getcontains") && commArray.length == 2) {
                    System.out.println((search.getFiles(commArray[1])));
                    continue;
                }

                if (commArray[0].equals("getfileswe") && commArray.length == 2) {
                    System.out.println((search.getFilesWithExtension(commArray[1])));
                    continue;
                }

                if (commArray[0].equals("getfilesbetween") && commArray.length == 4) {
                    System.out.println((search.getFiles(commArray[1], new Date(commArray[2]), new Date(commArray[3]))));
                    continue;
                }

                System.out.println("Incorrect command! For help use help command.");

            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static void help() {
        System.out.println("INFO:");
        System.out.println("    Path within storage must be relative");
        System.out.println("    Empty path is marked as '.'");
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
        System.out.println("    Get Directory - getdir path");
        System.out.println("    Get All Files - getallfiles");
        System.out.println("    Get All Files - getallfiles path");
        System.out.println("    Get Files That Contain String - getfilespodstring podstring");
        System.out.println("    Get All Files With Extention - getfileswe path e");
        System.out.println("    Contains Files? - getContains path file_names");
        System.out.println("    Get Files In Between Dates - getfilesbetween path start_date end_date");

        System.out.println("OPERATIONS:");
        System.out.println("    Delete Directory - deletedir path");
        System.out.println("    Delete File - deletefile path");
        System.out.println("    Delete All - deleteall root_path");
        System.out.println("    Download File - download from_path to_path");
        System.out.println("    Move File - movefiles from_path to_path");
//        System.out.println("    Get Sort By - sort path criteria -> sortByName, sortByDate, sortByModification and combinations ex. sortByName-sortByDate");
    }

    private static void printFiles(List<File> metadataList) {
        for (File metadata: metadataList) {
            try {
                printFile(metadata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void  printFile(File file) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

        System.out.println(file.getName() + " creationTime:" + attr.creationTime() + " lastModifiedTime:" + attr.lastModifiedTime());
    }
}
