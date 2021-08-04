package com.tensquare.encrypt.rsa;

/**
 * rsa加解密用的公钥和私钥
 * @author Administrator
 *
 */
public class RsaKeys {

	//生成秘钥对的方法可以参考这篇帖子
	//https://www.cnblogs.com/yucy/p/8962823.html

//	//服务器公钥
//	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Dw9nwjBmDD/Ca1QnRGy"
//											 + "GjtLbF4CX2EGGS7iqwPToV2UUtTDDemq69P8E+WJ4n5W7Iln3pgK+32y19B4oT5q"
//											 + "iUwXbbEaAXPPZFmT5svPH6XxiQgsiaeZtwQjY61qDga6UH2mYGp0GbrP3i9TjPNt"
//											 + "IeSwUSaH2YZfwNgFWqj+y/0jjl8DUsN2tIFVSNpNTZNQ/VX4Dln0Z5DBPK1mSskd"
//											 + "N6uPUj9Ga/IKnwUIv+wL1VWjLNlUjcEHsUE+YE2FN03VnWDJ/VHs7UdHha4d/nUH"
//											 + "rZrJsKkauqnwJsYbijQU+a0HubwXB7BYMlKovikwNpdMS3+lBzjS5KIu6mRv1GoE"
//											 + "vQIDAQAB";
//
//	//服务器私钥(经过pkcs8格式处理)
//	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDoPD2fCMGYMP8J"
//				 								 + "rVCdEbIaO0tsXgJfYQYZLuKrA9OhXZRS1MMN6arr0/wT5YniflbsiWfemAr7fbLX"
//				 								 + "0HihPmqJTBdtsRoBc89kWZPmy88fpfGJCCyJp5m3BCNjrWoOBrpQfaZganQZus/e"
//				 								 + "L1OM820h5LBRJofZhl/A2AVaqP7L/SOOXwNSw3a0gVVI2k1Nk1D9VfgOWfRnkME8"
//				 								 + "rWZKyR03q49SP0Zr8gqfBQi/7AvVVaMs2VSNwQexQT5gTYU3TdWdYMn9UeztR0eF"
//				 								 + "rh3+dQetmsmwqRq6qfAmxhuKNBT5rQe5vBcHsFgyUqi+KTA2l0xLf6UHONLkoi7q"
//				 								 + "ZG/UagS9AgMBAAECggEBANP72QvIBF8Vqld8+q7FLlu/cDN1BJlniReHsqQEFDOh"
//				 								 + "pfiN+ZZDix9FGz5WMiyqwlGbg1KuWqgBrzRMOTCGNt0oteIM3P4iZlblZZoww9nR"
//				 								 + "sc4xxeXJNQjYIC2mZ75x6bP7Xdl4ko3B9miLrqpksWNUypTopOysOc9f4FNHG326"
//				 								 + "0EMazVaXRCAIapTlcUpcwuRB1HT4N6iKL5Mzk3bzafLxfxbGCgTYiRQNeRyhXOnD"
//				 								 + "eJox64b5QkFjKn2G66B5RFZIQ+V+rOGsQElAMbW95jl0VoxUs6p5aNEe6jTgRzAT"
//				 								 + "kqM2v8As0GWi6yogQlsnR0WBn1ztggXTghQs2iDZ0YkCgYEA/LzC5Q8T15K2bM/N"
//				 								 + "K3ghIDBclB++Lw/xK1eONTXN+pBBqVQETtF3wxy6PiLV6PpJT/JIP27Q9VbtM9UF"
//				 								 + "3lepW6Z03VLqEVZo0fdVVyp8oHqv3I8Vo4JFPBDVxFiezygca/drtGMoce0wLWqu"
//				 								 + "bXlUmQlj+PTbXJMz4VTXuPl1cesCgYEA6zu5k1DsfPolcr3y7K9XpzkwBrT/L7LE"
//				 								 + "EiUGYIvgAkiIta2NDO/BIPdsq6OfkMdycAwkWFiGrJ7/VgU+hffIZwjZesr4HQuC"
//				 								 + "0APsqtUrk2yx+f33ZbrS39gcm/STDkVepeo1dsk2DMp7iCaxttYtMuqz3BNEwfRS"
//				 								 + "kIyKujP5kfcCgYEA1N2vUPm3/pNFLrR+26PcUp4o+2EY785/k7+0uMBOckFZ7GIl"
//				 								 + "FrV6J01k17zDaeyUHs+zZinRuTGzqzo6LSCsNdMnDtos5tleg6nLqRTRzuBGin/A"
//				 								 + "++xWn9aWFT+G0ne4KH9FqbLyd7IMJ9R4gR/1zseH+kFRGNGqmpi48MS61G0CgYBc"
//				 								 + "PBniwotH4cmHOSWkWohTAGBtcNDSghTRTIU4m//kxU4ddoRk+ylN5NZOYqTxXtLn"
//				 								 + "Tkt9/JAp5VoW/41peCOzCsxDkoxAzz+mkrNctKMWdjs+268Cy4Nd09475GU45khb"
//				 								 + "Y/88qV6xGz/evdVW7JniahbGByQhrMwm84R9yF1mNwKBgCIJZOFp9xV2997IY83S"
//				 								 + "habB/YSFbfZyojV+VFBRl4uc6OCpXdtSYzmsaRcMjN6Ikn7Mb9zgRHR8mPmtbVfj"
//				 								 + "B8W6V1H2KOPfn/LAM7Z0qw0MW4jimBhfhn4HY30AQ6GeImb2OqOuh3RBbeuuD+7m"
//				 								 + "LpFZC9zGggf9RK3PfqKeq30q";

	//服务器公钥
	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwxWj6uByE7jrWBHN4DNu\n" +
			"mVsSO1BnvJ6Y3E7JZU1w4lcp8Q3xhwNrwld1Rr0CDld58AIb/Mgszp0uK0Z10pMN\n" +
			"KVgGvlQHBUE51t4Fqj2Reki68yj3+8UqioUTsXqbEnKZg2+Vd46fyoCiUQy99oHo\n" +
			"MD1WQ3mdXs8hv84pskP7yiWQpb/r1YzY7u3CI4K+n78RT76dsK/vRQFWtLP2OXum\n" +
			"C25oTk1ulL38JHWBQ500e7Z82rTHNX7vYbjZu6QEFRVYFBgNhxzQvvt6vi6Bqgif\n" +
			"/O1uBm5zUyMOavE6icnNQcv7rWVxwY9c+PgXgCGS2XMiSkOEXOALSVJc+UMEqceh\n" +
			"SwIDAQAB";

	//服务器私钥(经过pkcs8格式处理)
	private static final String serverPrvKeyPkcs8 = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDDFaPq4HITuOtY\n" +
			"Ec3gM26ZWxI7UGe8npjcTsllTXDiVynxDfGHA2vCV3VGvQIOV3nwAhv8yCzOnS4r\n" +
			"RnXSkw0pWAa+VAcFQTnW3gWqPZF6SLrzKPf7xSqKhROxepsScpmDb5V3jp/KgKJR\n" +
			"DL32gegwPVZDeZ1ezyG/zimyQ/vKJZClv+vVjNju7cIjgr6fvxFPvp2wr+9FAVa0\n" +
			"s/Y5e6YLbmhOTW6UvfwkdYFDnTR7tnzatMc1fu9huNm7pAQVFVgUGA2HHNC++3q+\n" +
			"LoGqCJ/87W4GbnNTIw5q8TqJyc1By/utZXHBj1z4+BeAIZLZcyJKQ4Rc4AtJUlz5\n" +
			"QwSpx6FLAgMBAAECggEANUrBF5WutCKM7+cVNYz3Rp9xUJC7dfRhT7WaILlSABLN\n" +
			"m1Myyq4tjs5zdBqH6EEPR3hcCuboDp0zkpDR+O9A5i1s0iuEK3RRB5hpNcNR7XTW\n" +
			"1Tw42idXYrXN1mrm19hEhXpC+4ETt68CKF/xuNhuBFeNLCuZyN+OmaYJD/CwZNz5\n" +
			"6gLAowakVelkaKZpmkqG8OASLv/JbaTlXMYOx6u4mH3Xi6h65kENqfWUXLS0MfFV\n" +
			"oj1PQw1Px66iSVT7g1apbMOMg2OFVpcRX3+IiZ3WOhcmW3S1Y4NOO6XsVOz7gUae\n" +
			"LZJHp3wUf/v8tqQGjETrO464SB06/05rEHMv4njLuQKBgQDsCQ3ztamY5Aa641hV\n" +
			"5SQdFU77PGHsNnA0WEnZYb1GpiM6zGBGYXZ1Tk5nd7dY3XL/IhnTRcXRWM097sl2\n" +
			"xRhxZtpg7LUmX2r2ppF4+9CNxxL3Gjjz4y6facn5LTLODH7nKh3lMUYiPrP70rP8\n" +
			"1jEuI6B0e/k2HwTAsBoHl6GM1wKBgQDTld1qXVs3SJRCb0xzurQaZs57GgingbqL\n" +
			"es8LGeneA0QOYpinlQQBtcaTdwfc7KxeOdQkkCcfw+CkORTEPuXTTzWQBRAScR5D\n" +
			"6LLloYBp1l8ny85NWAIFhPY82GN0xe/BrKQImVO2A9uDYZL51O+C3X8v+5dTvBti\n" +
			"2PM37n2srQKBgQCkphxJaOuJb2cSbwxrvo/WicqyjohwUfkrSnsZXIjAA5yHo5F1\n" +
			"Bv+vDJS8sFIBd1iEiLEjI4S+aAejsDJw20QVtv2WpwgmYGEo9UB8+IuemsOsA2q/\n" +
			"FjarlQwC+Xh7K1RKxKJeAmU99idu/qf3MyLTYJ7JIsHQ8wpJr444xZk3uQKBgQDA\n" +
			"XO0bqdqnTV41dDjesqzRoAwzkTyzBdfSvYmE/z9HcFKsfWlqWql04I20PjCw45/m\n" +
			"Xs8HmuNnFs+inp7JuKF8VvRMhr3Q9nTcqrAS45z7HEIQzVM3sD7OQ88XDGiHnvlW\n" +
			"bJ4Jj1SzE5i8PrbAWp6SrMvYlPVE1K1VVgbyH+q0VQKBgQDJBmWaqsyj/QvRV9FU\n" +
			"w3oQZoHasKgh3GZDj+zC8Eai6qokasOpTYcG1E/G214VkSZWFsCTeZEpaGruS8wm\n" +
			"u9P0jfkqWWtI/yAzh7IIJPOp5vlk5RCa3P3bzpv/uRYCAIqh9UMzc7QZXPynlBbL\n" +
			"5KkyJPYzwj6S9SCaczV2MibRNQ==";

	public static String getServerPubKey() {
		return serverPubKey;
	}

	public static String getServerPrvKeyPkcs8() {
		return serverPrvKeyPkcs8;
	}
	
}
